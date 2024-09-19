package Services;

// @author Vitalii
// @project company-service
// @class CompanyService
// @version  1.0.0
// @since 12.09.2024 - 4:29

import Models.Company;
import java.util.List;

interface ICompanyService {

    /**
     * @param child company for whom we are searching the top-level parent (parent of parent, etc.)
     * @return top-level parent company
     */
    Company getTopLevelParent(Company child);

    /**
     * @param company   company for whom we are searching the count of employees (count of this company employees + count of employees for all children companies and their children, etc.)
     * @param companies all available companies
     * @return count of employees
     */
    long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies);
}

class CompanyService implements ICompanyService {

    @Override
    public Company getTopLevelParent(Company child) {
        // Traverse up the parent chain to find the top-level parent
        Company current = child;
        while (current.getParent() != null) {
            current = current.getParent();
        }
        return current;
    }

    @Override
    public long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies) {
        long totalEmployeeCount = company.getEmployeeCount();

        // Add employee counts of all child companies
        for (Company childCompany : companies) {
            // If the child's parent is the given company, it's a child company
            if (childCompany.getParent() == company) {
                totalEmployeeCount += getEmployeeCountForCompanyAndChildren(childCompany, companies);
            }
        }

        return totalEmployeeCount;
    }
}