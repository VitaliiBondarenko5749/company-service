package Services;

import Models.Company;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

// @author Vitalii
// @project company-service
// @class CompanyServiceTest
// @version  1.0.0
// @since 12.09.2024 - 4:34

import static org.junit.jupiter.api.Assertions.*;

class CompanyServiceTest {

    private CompanyService companyService;
    private List<Company> companies;

    @BeforeEach
    void setUp() {
        companyService = new CompanyService();
        companies = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        companies.clear();
    }

    @Test
    void testGetTopLevelParent_NoParent() {
        Company company = new Company(null, 100);
        assertEquals(company, companyService.getTopLevelParent(company));
    }

    @Test
    void testGetTopLevelParent_OneParent() {
        Company parent = new Company(null, 200);
        Company child = new Company(parent, 100);
        assertEquals(parent, companyService.getTopLevelParent(child));
    }

    @Test
    void testGetTopLevelParent_MultiLevel() {
        Company grandParent = new Company(null, 300);
        Company parent = new Company(grandParent, 200);
        Company child = new Company(parent, 100);
        assertEquals(grandParent, companyService.getTopLevelParent(child));
    }

    @Test
    void testGetTopLevelParent_GrandChild() {
        Company grandParent = new Company(null, 300);
        Company parent = new Company(grandParent, 200);
        Company child = new Company(parent, 100);
        Company grandChild = new Company(child, 50);
        assertEquals(grandParent, companyService.getTopLevelParent(grandChild));
    }

    @Test
    void testGetEmployeeCountForCompany_NoChildren() {
        Company company = new Company(null, 100);
        companies.add(company);
        assertEquals(100, companyService.getEmployeeCountForCompanyAndChildren(company, companies));
    }

    @Test
    void testGetEmployeeCountForCompany_OneChild() {
        Company parent = new Company(null, 100);
        Company child = new Company(parent, 50);
        companies.add(parent);
        companies.add(child);
        assertEquals(150, companyService.getEmployeeCountForCompanyAndChildren(parent, companies));
    }

    @Test
    void testGetEmployeeCountForCompany_MultipleChildren() {
        Company parent = new Company(null, 100);
        Company child1 = new Company(parent, 50);
        Company child2 = new Company(parent, 75);
        companies.add(parent);
        companies.add(child1);
        companies.add(child2);
        assertEquals(225, companyService.getEmployeeCountForCompanyAndChildren(parent, companies));
    }

    @Test
    void testGetEmployeeCountForCompany_DeepHierarchy() {
        Company grandParent = new Company(null, 300);
        Company parent = new Company(grandParent, 200);
        Company child = new Company(parent, 100);
        companies.add(grandParent);
        companies.add(parent);
        companies.add(child);
        assertEquals(600, companyService.getEmployeeCountForCompanyAndChildren(grandParent, companies));
    }

    @Test
    void testGetTopLevelParent_SingleCompany() {
        Company company = new Company(null, 100);
        assertEquals(company, companyService.getTopLevelParent(company));
    }

    @Test
    void testGetEmployeeCountForCompany_MultipleCompaniesNoChildren() {
        Company company1 = new Company(null, 100);
        Company company2 = new Company(null, 200);
        companies.add(company1);
        companies.add(company2);
        assertEquals(100, companyService.getEmployeeCountForCompanyAndChildren(company1, companies));
        assertEquals(200, companyService.getEmployeeCountForCompanyAndChildren(company2, companies));
    }

    @Test
    void testGetTopLevelParent_NoCircularReference() {
        Company company1 = new Company(null, 100);
        Company company2 = new Company(company1, 100);
        company1.setParent(null); // No circular reference

        // The top-level parent should be company1
        assertEquals(company1, companyService.getTopLevelParent(company2));
    }

    @Test
    void testGetEmployeeCountForCompany_MultipleBranches() {
        Company company1 = new Company(null, 100);
        Company company2 = new Company(company1, 200);
        Company company3 = new Company(company1, 300);
        companies.add(company1);
        companies.add(company2);
        companies.add(company3);
        assertEquals(600, companyService.getEmployeeCountForCompanyAndChildren(company1, companies));
    }

    @Test
    void testGetEmployeeCountForCompany_EmptyList() {
        Company company = new Company(null, 100);
        companies.clear(); // No companies in the list
        assertEquals(100, companyService.getEmployeeCountForCompanyAndChildren(company, companies));
    }

    @Test
    void testGetEmployeeCountForCompany_NonRelatedCompanies() {
        Company parent = new Company(null, 100);
        Company unrelated = new Company(null, 50);
        companies.add(parent);
        companies.add(unrelated); // unrelated company should not affect the result
        assertEquals(100, companyService.getEmployeeCountForCompanyAndChildren(parent, companies));
    }

    @Test
    void testGetTopLevelParent_NullChild() {
        assertThrows(NullPointerException.class, () -> companyService.getTopLevelParent(null));
    }

    @Test
    void testGetEmployeeCountForCompany_NullCompany() {
        assertThrows(NullPointerException.class, () -> companyService.getEmployeeCountForCompanyAndChildren(null, companies));
    }

    @Test
    void testGetEmployeeCountForCompany_NullCompanyList() {
        Company company = new Company(null, 100);
        assertThrows(NullPointerException.class, () -> companyService.getEmployeeCountForCompanyAndChildren(company, null));
    }

    @Test
    void testGetEmployeeCountForCompany_OrphanCompany() {
        Company company = new Company(null, 50);
        companies.add(company); // No parent, just the company itself
        assertEquals(50, companyService.getEmployeeCountForCompanyAndChildren(company, companies));
    }

    @Test
    void testGetTopLevelParent_DeepChain() {
        Company grandParent = new Company(null, 500);
        Company parent = new Company(grandParent, 300);
        Company child = new Company(parent, 100);
        Company grandChild = new Company(child, 50);
        assertEquals(grandParent, companyService.getTopLevelParent(grandChild));
    }

    @Test
    void testGetEmployeeCountForCompany_Grandchildren() {
        Company grandParent = new Company(null, 400);
        Company parent = new Company(grandParent, 200);
        Company child = new Company(parent, 100);
        companies.add(grandParent);
        companies.add(parent);
        companies.add(child);
        assertEquals(700, companyService.getEmployeeCountForCompanyAndChildren(grandParent, companies));
    }
}