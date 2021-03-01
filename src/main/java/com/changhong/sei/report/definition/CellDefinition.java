package com.changhong.sei.report.definition;

import com.changhong.sei.report.definition.value.Value;
import com.changhong.sei.report.enums.Expand;
import com.changhong.sei.report.expression.model.Expression;
import com.changhong.sei.report.model.Cell;
import com.changhong.sei.report.model.Range;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc：单元格模板
 * @author：Zhaohz
 * @date：2020/6/28 14:13
 */
public class CellDefinition implements Serializable {

    /**
     * 行数
     */
    private Integer rowNumber = 1;
    /**
     * 列数
     */
    private Integer columnNumber = 1;
    /**
     * 行单元格所跨行数
     */
    private Integer rowSpan = 0;
    /**
     * 列单元格所跨列数
     */
    private Integer colSpan = 0;
    /**
     * 元素名（一般指单元格名或序号）
     */
    private String name;
    /**
     * 元素的值，有数据集、图形、表达式、图片、常量、斜线分割框和条码等类型
     */
    private Value value;
    /**
     * 元素样式
     */
    private CellStyle cellStyle=new CellStyle();
    /**
     * 超链接Url
     */
    private String linkUrl;
    /**
     * 超链接目标窗口，包含父窗口、当前窗口、新窗口和顶层窗口4种 todo 可做枚举
     */
    private String linkTargetWindow;
    /**
     * 超链接参数集合
     */
    private List<LinkParameter> linkParameters;

    @JsonIgnore
    private Expression linkUrlExpression;
    /**
     * 允许填充空白行时fillBlankRows=true，要求当前数据行数必须是multiple定义的行数的倍数，否则就补充空白行
     */
    private Boolean fillBlankRows = Boolean.FALSE;

    private Integer multiple;

    private Expand expand= Expand.None;

    @JsonIgnore
    private Range duplicateRange;
    @JsonIgnore
    private List<String> increaseSpanCellNames=new ArrayList<String>();
    @JsonIgnore
    private Map<String, BlankCellInfo> newBlankCellsMap=new HashMap<String, BlankCellInfo>();
    @JsonIgnore
    private List<String> newCellNames=new ArrayList<String>();

    /**
     * 当前单元格左父格名
     */
    private String leftParentCellName;
    /**
     * 当前单元格上父格名
     */
    private String topParentCellName;
    /**
     * 当前单元格左父格
     */
    @JsonIgnore
    private CellDefinition leftParentCell;
    /**
     * 当前单元格上父格
     */
    @JsonIgnore
    private CellDefinition topParentCell;
    /**
     * 当前单无格所在行的所有子格
     */
    @JsonIgnore
    private List<CellDefinition> rowChildrenCells=new ArrayList<CellDefinition>();
    /**
     * 当前单无格所在列的所有子格
     */
    @JsonIgnore
    private List<CellDefinition> columnChildrenCells=new ArrayList<CellDefinition>();

    private List<ConditionPropertyItem> conditionPropertyItems;

    protected Cell newCell(){
        Cell cell=new Cell();
        cell.setValue(value);
        cell.setName(name);
        cell.setRowSpan(rowSpan);
        cell.setColSpan(colSpan);
        cell.setExpand(expand);
        cell.setCellStyle(cellStyle);
        cell.setNewBlankCellsMap(newBlankCellsMap);
        cell.setIncreaseSpanCellNames(increaseSpanCellNames);
        cell.setNewCellNames(newCellNames);
        cell.setDuplicateRange(duplicateRange);
        cell.setLinkParameters(linkParameters);
        cell.setLinkTargetWindow(linkTargetWindow);
        cell.setLinkUrl(linkUrl);
        cell.setConditionPropertyItems(conditionPropertyItems);
        cell.setFillBlankRows(fillBlankRows);
        if (fillBlankRows) {
            cell.setMultiple(multiple);
        }
        cell.setLinkUrlExpression(linkUrlExpression);
        return cell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }

    public Integer getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(Integer rowSpan) {
        this.rowSpan = rowSpan;
    }

    public Integer getColSpan() {
        return colSpan;
    }

    public void setColSpan(Integer colSpan) {
        this.colSpan = colSpan;
    }

    public Boolean getFillBlankRows() {
        return fillBlankRows;
    }

    public void setFillBlankRows(Boolean fillBlankRows) {
        this.fillBlankRows = fillBlankRows;
    }

    public Integer getMultiple() {
        return multiple;
    }

    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Expand getExpand() {
        return expand;
    }

    public void setExpand(Expand expand) {
        this.expand = expand;
    }

    public String getLeftParentCellName() {
        return leftParentCellName;
    }

    public void setLeftParentCellName(String leftParentCellName) {
        this.leftParentCellName = leftParentCellName;
    }

    public String getTopParentCellName() {
        return topParentCellName;
    }

    public void setTopParentCellName(String topParentCellName) {
        this.topParentCellName = topParentCellName;
    }

    public CellDefinition getLeftParentCell() {
        return leftParentCell;
    }

    public void setLeftParentCell(CellDefinition leftParentCell) {
        this.leftParentCell = leftParentCell;
    }

    public CellDefinition getTopParentCell() {
        return topParentCell;
    }

    public void setTopParentCell(CellDefinition topParentCell) {
        this.topParentCell = topParentCell;
    }

    public CellStyle getCellStyle() {
        return cellStyle;
    }

    public Range getDuplicateRange() {
        return duplicateRange;
    }

    public void setDuplicateRange(Range duplicateRange) {
        this.duplicateRange = duplicateRange;
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    public List<CellDefinition> getRowChildrenCells() {
        return rowChildrenCells;
    }
    public List<CellDefinition> getColumnChildrenCells() {
        return columnChildrenCells;
    }
    public List<String> getIncreaseSpanCellNames() {
        return increaseSpanCellNames;
    }
    public Map<String, BlankCellInfo> getNewBlankCellsMap() {
        return newBlankCellsMap;
    }
    public List<String> getNewCellNames() {
        return newCellNames;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getLinkTargetWindow() {
        return linkTargetWindow;
    }

    public void setLinkTargetWindow(String linkTargetWindow) {
        this.linkTargetWindow = linkTargetWindow;
    }

    public List<LinkParameter> getLinkParameters() {
        return linkParameters;
    }

    public void setLinkParameters(List<LinkParameter> linkParameters) {
        this.linkParameters = linkParameters;
    }
    public List<ConditionPropertyItem> getConditionPropertyItems() {
        return conditionPropertyItems;
    }
    public void setConditionPropertyItems(
            List<ConditionPropertyItem> conditionPropertyItems) {
        this.conditionPropertyItems = conditionPropertyItems;
    }
    public Expression getLinkUrlExpression() {
        return linkUrlExpression;
    }
    public void setLinkUrlExpression(Expression linkUrlExpression) {
        this.linkUrlExpression = linkUrlExpression;
    }
}