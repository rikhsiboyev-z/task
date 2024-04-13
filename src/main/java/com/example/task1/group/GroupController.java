package com.example.task1.group;

import com.example.task1.group.dto.GroupCreateDto;
import com.example.task1.group.dto.GroupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;


    @PostMapping
    public ResponseEntity<GroupResponseDto> createGroup(@RequestBody GroupCreateDto groupCreateDto) {

        GroupResponseDto groupResponseDto = groupService.create(groupCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(groupResponseDto);

    }

    @GetMapping
    public ResponseEntity<Page<GroupResponseDto>> getALl(Pageable pageable) {

        Page<GroupResponseDto> page = groupService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Integer id) {
        groupService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted group with id " + id);
    }
}
