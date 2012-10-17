import javax.swing.table.AbstractTableModel;


public class TableModificationPresence extends AbstractTableModel{

	private static final long serialVersionUID = 1L;

	public TableModificationPresence(){
		super();		
	}

	public String getColumnName(int column) {
		if(column == 0){
			return "Nom";	
		}else if(column == 1){
			return "Présent";
		}else{
			return "Excusé";
		}

	}


	public boolean isCellEditable(int row, int column)
	{
		switch (column) {
		case 0:
			return false;
		case 1:
			return true;
		default:
			return true;		
		}
	}

	public void setValueAt(Object aVal, int row, int col){
		if(col == 1){
			if(!Main.fenetreControle.getListeEtudiants().get(row).getPresenceMyFare() &&
					!Main.fenetreControle.getListeEtudiants().get(row).getExcuse())
				Main.fenetreControle.getListeEtudiants().get(row).setPresent((Boolean) aVal);
		}else if(col == 2){
			if(!Main.fenetreControle.getListeEtudiants().get(row).getPresent())
				Main.fenetreControle.getListeEtudiants().get(row).setExcuse((Boolean) aVal);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int column) {
		switch (column) {
		case 0:
			return String.class;
		case 1:
			return Boolean.class;
		case 2:
			return Boolean.class;
		default:
			return Boolean.class;
		}
	}



	@Override
	public int getColumnCount() {
		//c'est constant
		return 3;
	}

	@Override
	public int getRowCount() {
		return Main.fenetreControle.getListeEtudiants().size();
	}



	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub

		if(arg1 == 0){
			return (Object) Main.fenetreControle.getListeEtudiants().get(arg0).getNom() + " " + Main.fenetreControle.getListeEtudiants().get(arg0).getPrenom(); 
		}else if(arg1 == 1){
			return (Object) Main.fenetreControle.getListeEtudiants().get(arg0).getPresent();
		}else{
			return (Object) Main.fenetreControle.getListeEtudiants().get(arg0).getExcuse();
		}
	}

}
