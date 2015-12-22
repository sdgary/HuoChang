//package byxx.android.huochang.dw;
//
//import java.awt.Color;
//import java.awt.Component;
//
//import javax.swing.JTable;
//import javax.swing.table.DefaultTableCellRenderer;
//
//import byxx.scds.common.ScdsOptions;
//import byxx.scds.dto.XGjhzwDto;
//import byxx.scds.qsjh.model.TmQsGjh;
//
///**
// * @date 2014-2-24
// * @author XLei
// */
//
//public class QsGjhColorRender extends DefaultTableCellRenderer {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1175670714255324403L;
//	private TmQsGjh tm;
//
//	public QsGjhColorRender(TmQsGjh tmQsGjh) {
//		super();
//		this.tm = tmQsGjh;
//	}
//
//	public Component getTableCellRendererComponent(JTable table, Object value,
//			boolean isSelected, boolean hasFocus, int row, int col) {
//		Component cp = super.getTableCellRendererComponent(table, value,
//				isSelected, hasFocus, row, col);
//		XGjhzwDto gjh = tm.getShowdatas().get(row);
//		if (gjh.getNotifyTime() != null && gjh.getConfirmTime() == null
//				&& gjh.getFactTime() == null) {
//			cp.setForeground(Color.red);
//		} else if (gjh.getNotifyTime() != null && gjh.getConfirmTime() != null
//				&& gjh.getFactTime() == null) {
//			cp.setForeground(Color.blue);
//		} else if (gjh.getNotifyTime() != null && gjh.getConfirmTime() != null
//				&& gjh.getFactTime() != null) {
//			cp.setForeground(ScdsOptions.darkGreen);
//		} else {
//			cp.setForeground(Color.black);
//		}
//		return cp;
//	}
//}
