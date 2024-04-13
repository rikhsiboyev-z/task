package com.example.task1.group;

import com.example.task1.group.dto.GroupCreateDto;
import com.example.task1.group.dto.GroupResponseDto;
import com.example.task1.group.entity.Group;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    public GroupResponseDto create(GroupCreateDto groupCreateDto) {

        Group group = modelMapper.map(groupCreateDto, Group.class);
        return modelMapper.map(groupRepository.save(group), GroupResponseDto.class);
    }


    public Page<GroupResponseDto> getAll(Pageable pageable) {

        return groupRepository.findAll(pageable).map(group -> modelMapper.map(group, GroupResponseDto.class));
    }

    public void delete(Integer id) {
        groupRepository.deleteById(id);
    }
}
