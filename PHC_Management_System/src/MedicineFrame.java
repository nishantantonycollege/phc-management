public class MedicineFrame extends ManageFrame {
    public MedicineFrame() {
        super("Medicine Management",
              new String[] {"ID", "Name", "Quantity", "Price"},
              "medicines.csv",
              new int[] {2, 3}); // Quantity and Price must be numbers
    }
}
