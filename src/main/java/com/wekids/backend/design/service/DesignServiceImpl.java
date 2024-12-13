package com.wekids.backend.design.service;

import com.wekids.backend.alarm.domain.Alarm;
import com.wekids.backend.alarm.repository.AlarmRepository;
import com.wekids.backend.design.domain.Design;
import com.wekids.backend.design.dto.request.DesignCreateRequest;
import com.wekids.backend.design.dto.response.DesignResponse;
import com.wekids.backend.design.repository.DesignRepository;
import com.wekids.backend.exception.ErrorCode;
import com.wekids.backend.exception.WekidsException;
import com.wekids.backend.member.domain.Child;
import com.wekids.backend.member.domain.Parent;
import com.wekids.backend.member.domain.enums.CardState;
import com.wekids.backend.member.domain.mapping.ParentChild;
import com.wekids.backend.member.repository.ChildRepository;
import com.wekids.backend.member.repository.ParentChildRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DesignServiceImpl implements DesignService {

    private final DesignRepository designRepository;
    private final ChildRepository childRepository;
    private final AlarmRepository alarmRepository;
    private final ParentChildRepository parentChildRepository;

    @Override
    public DesignResponse showDesign(Long designId) {
        Design design = getDesign(designId);
        return DesignResponse.of(design.getColor().name(), design.getCharacter().name());
    }

    @Override
    @Transactional
    public void createDesign(Long childId, DesignCreateRequest request) {
        Child child = getChild(childId);
        ParentChild relationship = findRelationshipByChild(child);
        Parent parent = relationship.getParent();

        validateCardState(child);
        child.updateCardState(CardState.READY);

        Design newDesign = Design.create(child, request.getColor(), request.getCharacter());

        Design savedDesign = designRepository.save(newDesign);

        child.saveProfile(savedDesign.getCharacter());

        Alarm alarm = Alarm.createCardDesignedAlarm(child, parent);
        alarmRepository.save(alarm);
    }

    private void validateCardState(Child child) {
        if(!child.getCardState().equals(CardState.NONE)) throw new WekidsException(ErrorCode.INVALID_CARD_STATE, "현재 카드 상태: " + child.getCardState() + "변경하려는 카드 상태: " + CardState.READY);
    }

    private Child getChild(Long memberId) {
        return childRepository.findById(memberId).orElseThrow(() -> new WekidsException(ErrorCode.MEMBER_NOT_FOUND, "아이디: " + memberId));
    }

    private Design getDesign(Long designId) {
        return designRepository.findById(designId)
                .orElseThrow(() -> new WekidsException(ErrorCode.DESIGN_NOT_FOUND, "디자인 아이디: " + designId));
    }

    private ParentChild findRelationshipByChild(Child child) {
        return parentChildRepository.findParentChildByChild(child).orElseThrow(() -> new WekidsException(ErrorCode.RELATIONSHIP_NOT_FOUND, "자식 회원 아이디: " + child.getId()));
    }

}