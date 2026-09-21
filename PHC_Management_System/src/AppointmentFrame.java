public class AppointmentFrame extends ManageFrame {
    public AppointmentFrame() {
        super("Appointment Management",
              new String[] {"ID", "Patient", "Doctor", "Date", "Time"},
              "appointments.csv",
              new int[] {});
    }
}
