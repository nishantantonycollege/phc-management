public class DoctorFrame extends ManageFrame {
    public DoctorFrame() {
        super("Doctor Management",
              new String[] {"ID", "Name", "Specialization", "Phone"},
              "doctors.csv",
              new int[] {});
    }
}
