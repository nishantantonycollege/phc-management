public class EquipmentFrame extends ManageFrame {
    public EquipmentFrame() {
        super("Equipment Management",
              new String[] {"ID", "Name", "Quantity", "Status"},
              "equipment.csv",
              new int[] {2}); // Quantity must be a number
    }
}
