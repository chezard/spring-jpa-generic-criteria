package com.beyt.jdq.presetation;

import com.beyt.jdq.BaseTestInstance;
import com.beyt.jdq.TestApplication;
import com.beyt.jdq.dto.Criteria;
import com.beyt.jdq.dto.CriteriaList;
import com.beyt.jdq.dto.enums.CriteriaOperator;
import com.beyt.jdq.testenv.entity.authorization.AdminUser;
import com.beyt.jdq.testenv.repository.AdminUserRepository;
import com.beyt.jdq.testenv.repository.DepartmentRepository;
import com.beyt.jdq.util.PresentationUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = TestApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class S6_Advenced_Join extends BaseTestInstance {
    private @Autowired AdminUserRepository adminUserRepository;
    private @Autowired DepartmentRepository departmentRepository;

    @Test
    public void roleJoin() {
        var criteriaList = CriteriaList.of(Criteria.of("roles.roleAuthorizations.authorization.menuIcon", CriteriaOperator.START_WITH, "icon"));
        PresentationUtil.prettyPrint(criteriaList);
        List<AdminUser> adminUserList = adminUserRepository.findAll(criteriaList);
        PresentationUtil.prettyPrint(adminUserList);//1,2,3,4,5
        assertEquals(List.of(adminUser1, adminUser2, adminUser3, adminUser4, adminUser5), adminUserList);
    }

    @Test
    public void roleLeftJoin() {
        var criteriaList = CriteriaList.of(Criteria.of("roles<roleAuthorizations<authorization<menuIcon", CriteriaOperator.START_WITH, "icon"));
        PresentationUtil.prettyPrint(criteriaList);
        List<AdminUser> adminUserList = adminUserRepository.findAll(criteriaList);
        PresentationUtil.prettyPrint(adminUserList);
        assertEquals(List.of(adminUser1, adminUser2, adminUser3, adminUser4, adminUser5), adminUserList);
    }
}
