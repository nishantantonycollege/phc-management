public class EmployeeFrame extends ManageFrame {
    public EmployeeFrame() {
        super("Employee Management",
              new String[] {"ID", "Name", "Role", "Phone"},
              "employees.csv",
              new int[] {});
    }
}
