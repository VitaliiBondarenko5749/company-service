package Models;

// @author Vitalii
// @project company-service
// @class Company
// @version  1.0.0
// @since 12.09.2024 - 4:21

public class Company {
    // Parent for this company, nullable when there is no parent
    private Company parent;
    private long employeeCount;

    // Constructor
    public Company(Company parent, long employeeCount) {
        this.parent = parent;
        this.employeeCount = employeeCount;
    }

    // Getters and Setters
    public Company getParent() {
        return parent;
    }

    public void setParent(Company parent) {
        this.parent = parent;
    }

    public long getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(long employeeCount) {
        this.employeeCount = employeeCount;
    }
}