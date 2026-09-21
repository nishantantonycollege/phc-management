public class PatientFrame extends ManageFrame {
    public PatientFrame() {
        super("Patient Management",
              new String[] {"ID", "Name", "Age", "Gender", "Phone", "Disease"},
              "patients.csv",
              new int[] {2}); // Age must be a number
    }
}
