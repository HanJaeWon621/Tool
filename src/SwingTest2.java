import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class SwingTest2 extends JFrame {

    //메뉴 영역
    JMenu menu = new JMenu("파일");

    JMenu menu2 = new JMenu("편집");

    JMenu menu3 = new JMenu("실행");

    JMenuItem m01 = new JMenuItem("개발접속");

    JMenuItem m02 = new JMenuItem("테스트접속");

    JMenuItem m11 = new JMenuItem("열기");

    JMenuItem fileSaveMenu = new JMenuItem("저장");

    JMenuItem m13 = new JMenuItem("창닫기");

    JMenuItem copyToClipboardMenu = new JMenuItem("클립보드로 복사");

    JMenuItem clearStatementMenuFILE = new JMenuItem("File문장클리어");
    JMenuItem clearStatementMenuSQL = new JMenuItem("Sql문장클리어");

    JMenuItem m31 = new JMenuItem("오브젝트조회");

    JMenuItem m32 = new JMenuItem("문장추출");

    JMenuItem executeStatementMenu = new JMenuItem("문장실행");

    JMenuItem executeDescMenu = new JMenuItem("명세실행");

    JMenuItem executeProcMenu = new JMenuItem("프로시저실행");

    //메인 버튼
    JButton dbConnBtn = new JButton("DB연결");

    JButton searchBtn = new JButton("오브젝트조회(F1)");

    JButton showBtn = new JButton("문장추출(F2)");

    JButton openBtn = new JButton("파일열기(F3)");

    JButton saveBtn = new JButton("파일저장(CTRL+S)");

    JButton execBtn = new JButton("문장실행(F5)");

    JButton execDescBtn = new JButton("명세실행(F8)");

    JButton statementClearFILEBtn = new JButton("파일문장클리어(F12)");
    JButton statementClearSQLBtn = new JButton("SQL문장클리어(F10)");

    JButton clipBoardCopyBtn = new JButton("클립보드로복사(F9)");

    JButton exePrcBtn = new JButton("프로시저실행(F11)");

    //문장 구분 영역
    ButtonGroup radionBtnGroup = new ButtonGroup();

    String gSelRadio = "Select";

    JRadioButton rbSelect = new JRadioButton("Select");

    JRadioButton rbInsert = new JRadioButton("Insert");

    JRadioButton rbUpdate = new JRadioButton("Update");

    JRadioButton rbDelete = new JRadioButton("Delete");

    JRadioButton rbSelectDesc = new JRadioButton("SelectDesc");

    JRadioButton rbProcSave = new JRadioButton("ProcSave");

    JRadioButton rbProcSel = new JRadioButton("ProcSel");

    JRadioButton rbXmlSave = new JRadioButton("XmlSave");

    JRadioButton rbXmlSel = new JRadioButton("XmlSel");

    JRadioButton rbProcTableTypeVar = new JRadioButton("ProcTableTypeVar");

    JRadioButton rbProcTypeVarExt = new JRadioButton("ProcTableTypeVarExt");

    JRadioButton rbProcTableTypeVarUse = new JRadioButton("ProcTableTypeVarUse");

    JRadioButton rbDataSet = new JRadioButton("DataSet");

    JRadioButton rbColModel = new JRadioButton("ColModel");

    JTextField tfTableName = new JTextField("");

    //JTextField lInputTextField2 = new JTextField("");
    JTextField lInputTextField3 = new JTextField("");

    JTextField lInputTextField4 = new JTextField("");

    JTextField searchTextField = new JTextField("");

    //DB탭 영역
    String[] objStr = { "TABLE", "PROCEDURE", "FUNCTION", "VIEW", "TRIGGER", "" };//오브젝트 구분

    String[] dbConnStr = { "개발", "운영" };//DB연결정보

    String[] workDivStr = { "인사|ACB", "영업|SM", "시공협업|PMD","노무|PME",  "건설회계|ACA",  "공통|PMS_COM", "실행|PMA", "견적|ES",  "프로젝트포탈|PPA",  "SEQ|PMF", "AS|PMG","외주구매|CM",
            "공정|PMB", "원가|PMC",  "자료관리|DM", "PMR|PMR" };//업무구분자

    JComboBox objectComboBox = new JComboBox(objStr);

    JComboBox dbContComboBox = new JComboBox(dbConnStr);

    JComboBox dbWorkDivComboBox = new JComboBox(workDivStr);

    JTable objectTable = null;//new JTable(sample, header1);

    DefaultTableModel objectDtm;

    TableColumnModel objectTcm = null;

    Vector objectVector = new Vector();

    //탐색기 탭 디렉토리 영역
    JButton maxTopDirMoveBtn = new JButton("최상위이동");

    JButton topDirMoveBtn = new JButton("상위이동");

    JButton subDirMoveBtn = new JButton("서브폴더이동");

    JLabel tlDir = new JLabel("폴더명");

    JTextField tfDir = new JTextField("");

    JButton newDirBtn = new JButton("신규폴더");

    JButton delDirBtn = new JButton("폴더삭제");

    JLabel jlFileDirPath = new JLabel("경로");

    JTextField tfFileDirPath = new JTextField("");

    JTable directoryGridTable = null;

    DefaultTableModel directoryGridDtm;

    TableColumnModel directoryGridTcm = null;

    Vector directoryGridVector = new Vector();

    //탐색기 탭  파일  영역
    JTextField tfFileName = new JTextField("");

    JButton newFileBtn = new JButton("신규파일");

    JButton renameFileBtn = new JButton("이름변경");

    JButton delFileBtn = new JButton("파일삭제");

    JTable fileGridTable = null;

    DefaultTableModel fileGridDtm;

    TableColumnModel fileGridTcm = null;

    Vector fileGridVector = new Vector();

    //내용오브젝트 탭   영역
    JLabel jlObjectContent = new JLabel("내용");

    JTextField tfObjectContent = new JTextField("");

    JButton btnObjectContent = new JButton("검색");

    JTable objectContentGridTable = null;

    DefaultTableModel objectContentGridDtm;

    TableColumnModel objectContentGridTcm = null;

    Vector objectContentGridVector = new Vector();

    //내용 영역내 문장탭
    //JTextArea statementArea = new JTextArea();//일반문장
    JTextArea statementFILEArea = new JTextArea();//일반문장
    JTextArea statementSQLArea = new JTextArea();//일반문장

    //내용 영역내 문장실행 결과탭
    JTable resultTable = null;

    DefaultTableModel resultDtm;

    TableColumnModel resultTcm = null;

    Vector resultVector = new Vector();

    //내용 영역내 명세탭
    JLabel jlDescResult = new JLabel("명세목록");

    JTable descTable = null;

    DefaultTableModel descDtm;

    //TableModel descDtm;
    Vector descVector = new Vector();

    JLabel jlDescStatement = new JLabel("명세문장");

    JTextArea descStatementArea = new JTextArea();//명세문장

    //내용 영역내 명세실행결과탭
    JTable descExecResultTable = null;

    DefaultTableModel descExecResultDtm;

    TableColumnModel descExecResultTcm = null;

    Vector descExecResultVector = new Vector();

    //내용 영역내 명명탭
    JLabel jl1 = new JLabel("메소드업무구분");//0101

    JLabel jl2 = new JLabel("파일업무구분");

    JLabel jl3 = new JLabel("세부업무코드");

    JLabel jl4 = new JLabel("한글업무명");

    JTextField tf1 = new JTextField("JV_EBGT");

    JTextField tf2 = new JTextField("JV_EBGT");

    JTextField tf3 = new JTextField("1280");

    JTextField tf4 = new JTextField("업무");

    JButton nameSearchBtnSub = new JButton("명명조회");

    String headers3[] = { "구분", "서브구분", "소스파일명", "한글메소드", "영문메소드1" };

    JTable nameTable = null;

    private DefaultTableModel nameDtm;

    Vector nameVector = new Vector();

    //내용 영역내 엑셀탭
    JTable excelGridTable = null;

    DefaultTableModel excelDtm;

    TableColumnModel excelGridTcm = null;

    Vector excelVector = new Vector();

    //내용 영역내 프로시저 실행탭
    JTable resultProcParamTable = null;

    DefaultTableModel resultProcParamDtm;

    TableColumnModel resultProcParamTcm = null;

    Vector resultProcParamVector = new Vector();

    JTable resultProcTable = null;

    DefaultTableModel resultProcDtm;

    TableColumnModel resultProcTcm = null;

    Vector resultProcVector = new Vector();

    //자동디렉토리 생성패널
    JEditorPane jAutoDirEditPanel = new JEditorPane();

    static CodeMake cm = null;

    //파일 오픈
    JFileChooser fileChooser = new JFileChooser();

    //탭패널
    JTabbedPane conditionLeftTabPanel = null;

    JTabbedPane contentTabPanel = null;

    //DB접속 정보
    String url = "@165.244.179.99:1526:SVOCSTD";

    String userid = "cst_app";

    String pwd = "cstdpdlvlvl";

    static String tbName = "TBPMA067";//table명

    String user = "COP_MGR";//실dB계정

    static String sid = "개발";

    Connection conn = null;

    static Component component;

    JButton button = new JButton("확인");

    JPanel jp = new JPanel();

    JTree jw = new JTree();

    PopupFactory factory = PopupFactory.getSharedInstance();

    Popup popup = null;

    ListSelectionModel objectCellSelectionModel = null;

    ListSelectionModel descCellSelectionModel = null;

    ListSelectionModel objectContentCellSelectionModel = null;

    //디렉토리, 파일 전역 변수 E
    static String upDirPath = "C:" + File.separator + "CodeMakerFile";

    //static String upDirPath = "E:" + File.separator;
    static String upDir = "C:" + File.separator;

    //static String upDirPath = "E:\\";

    static String g_SubPath = "";

    static String g_UpPath = "";

    static String g_UpDir = "";

    Object rowData[][] = { { "1", "1", "1", "1", "1", "1", "1", Boolean.TRUE, Boolean.TRUE, "1" },
            { "1", "1", "1", "1", "1", "1", "1", Boolean.TRUE, Boolean.TRUE, "1" } };

    public SwingTest2() {
        cm = new CodeMake();
        //getConnection("개발");

        setUI();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run(){

                initUI();

            }
        });
        File dir = new File(upDirPath);
        if(!dir.isDirectory()){
            dir.mkdir();
        }
        /*
        selObjList("TABLE", "", "");
        selDirList(upDirPath, upDir);
        selFileList(upDirPath);
        tfFileDirPath.setText(upDirPath);
		*/
        this.setTitle("CodeMaker(개발)");
    }

    //레이아웃 생성을 위한 세팅
    private void setUI(){
        //팝업용 패널
        jp.setBounds(0, 0, 500, 500);

        objectDtm = new DefaultTableModel();
        objectDtm.addColumn("오브젝트", objectVector);
        objectDtm.addColumn("설명", objectVector);
        //objectDtm.addColumn("작성자", objectVector);
        objectTable = new JTable(objectDtm, objectTcm);

        nameDtm = new DefaultTableModel();
        nameDtm.addColumn("구분", nameVector);
        nameDtm.addColumn("서브구분", nameVector);
        nameDtm.addColumn("소스파일명", nameVector);
        nameDtm.addColumn("한글메소드", nameVector);
        nameDtm.addColumn("영문메소드", nameVector);
        nameTable = new JTable(nameDtm);

        directoryGridDtm = new DefaultTableModel();
        directoryGridDtm.addColumn("디렉토리", directoryGridVector);
        directoryGridDtm.addColumn("서브갯수", directoryGridVector);
        directoryGridDtm.addColumn("상위디렉토리", directoryGridVector);
        directoryGridDtm.addColumn("상위이동", directoryGridVector);
        directoryGridTable = new JTable(directoryGridDtm, directoryGridTcm);

        fileGridDtm = new DefaultTableModel();
        fileGridDtm.addColumn("파일", fileGridVector);
        fileGridDtm.addColumn("크기", fileGridVector);
        fileGridDtm.addColumn("일자", fileGridVector);
        fileGridDtm.addColumn("확장자", fileGridVector);
        fileGridTable = new JTable(fileGridDtm, fileGridTcm);

        objectContentGridDtm = new DefaultTableModel();
        objectContentGridDtm.addColumn("오브젝트", objectContentGridVector);
        objectContentGridDtm.addColumn("타입", objectContentGridVector);
        objectContentGridTable = new JTable(objectContentGridDtm, objectContentGridTcm);

        resultDtm = new DefaultTableModel();
        resultTable = new JTable();
        resultTable.setModel(resultDtm);
        resultTable.setAutoscrolls(true);
        resultTable.setAutoCreateColumnsFromModel(true);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        descDtm = new DefaultTableModel();
        //descDtm = new ColorTableModel();
        /*
        descDtm.addColumn("순번", descVector);
        descDtm.addColumn("컬럼", descVector);
        descDtm.addColumn("변수", descVector);
        descDtm.addColumn("주석", descVector);
        descDtm.addColumn("타입", descVector);
        descDtm.addColumn("길이", descVector);
        descDtm.addColumn("정밀도", descVector);
        descDtm.addColumn("본문", descVector);
        descDtm.addColumn("조건", descVector);
        descDtm.addColumn("조건값", descVector);
        */

        descDtm.addColumn("순번", new Object[] { new String() });
        //descDtm.addColumn("순번",   new Object[] { Boolean.TRUE });
        descDtm.addColumn("컬럼", new Object[] { new String() });
        descDtm.addColumn("변수", new Object[] { new String() });
        descDtm.addColumn("주석", new Object[] { new String() });
        descDtm.addColumn("타입", new Object[] { new String() });
        descDtm.addColumn("길이", new Object[] { new String() });
        descDtm.addColumn("정밀도", new Object[] { new String() });
        descDtm.addColumn("본문", new Object[] { Boolean.TRUE });
        descDtm.addColumn("조건", new Object[] { Boolean.TRUE });
        descDtm.addColumn("조건값", new Object[] { new String() });

        descTable = new JTable(descDtm);

        descTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        descExecResultDtm = new DefaultTableModel();
        descExecResultTable = new JTable(descExecResultDtm);
        descExecResultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        excelDtm = new DefaultTableModel();
        excelGridTable = new JTable(excelDtm);
        excelGridTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        resultProcParamDtm = new DefaultTableModel();
        resultProcParamDtm.addColumn("순번", resultProcParamVector);
        resultProcParamDtm.addColumn("변수", resultProcParamVector);
        resultProcParamDtm.addColumn("타입", resultProcParamVector);
        resultProcParamDtm.addColumn("입출력", resultProcParamVector);
        resultProcParamDtm.addColumn("값", resultProcParamVector);
        resultProcParamTable = new JTable(resultProcParamDtm, resultProcParamTcm);

        resultProcDtm = new DefaultTableModel();
        resultProcTable = new JTable();
        resultProcTable.setModel(resultProcDtm);
        resultProcTable.setAutoscrolls(true);
        resultProcTable.setAutoCreateColumnsFromModel(true);
        resultProcTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    }

    //레이아웃 그리기
    private void initUI(){
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("CodeMaker");
        this.setSize(1000, 800);

        conditionLeftTabPanel = new JTabbedPane();
        contentTabPanel = new JTabbedPane();
        //메뉴 추가
        JMenuBar bar = new JMenuBar();
        bar.add(menu);
        bar.add(menu2);
        bar.add(menu3);
        menu.add(m01);
        menu.add(m02);
        menu.add(m11);
        menu.add(fileSaveMenu);
        menu.add(new JSeparator()); // SEPARATOR
        menu.add(m13);

        menu2.add(copyToClipboardMenu);
        menu2.add(clearStatementMenuFILE);
        menu2.add(clearStatementMenuSQL);

        menu3.add(m31);
        menu3.add(m32);
        menu3.add(executeStatementMenu);
        menu3.add(executeDescMenu);
        menu3.add(executeProcMenu);
        setJMenuBar(bar);

        //JMenuItem에 단축키 추가
        copyToClipboardMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.getKeyText(KeyEvent.VK_F9)));
        clearStatementMenuFILE.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.getKeyText(KeyEvent.VK_F12)));
        clearStatementMenuSQL.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.getKeyText(KeyEvent.VK_F10)));
        fileSaveMenu.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));//KeyEvent.VK_F8
        executeStatementMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.getKeyText(KeyEvent.VK_F5)));
        executeDescMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.getKeyText(KeyEvent.VK_F8)));
        executeProcMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.getKeyText(KeyEvent.VK_F11)));
        JComponent c = (JPanel)this.getContentPane();

        c.setLayout(new BorderLayout());

        //메인 버튼용  패널 생성
        GridBagLayout gbl = new GridBagLayout();
        JPanel gbBtnPanel = new JPanel(gbl);
        GridBagConstraints gbc = new GridBagConstraints();

        //메인 버튼 생성
        gbc.gridx = 0;
        gbc.gridy = 0;
        // gbc.gridheight=5;
        gbc.fill = GridBagConstraints.BOTH; // 가로/세로 확장모드

        gbl.setConstraints(dbConnBtn, gbc);
        gbBtnPanel.add(dbConnBtn);

        gbc.gridx = 1;
        gbc.gridy = 0;
        //gbc.fill=GridBagConstraints.VERTICAL; // 가로/세로 확장모드

        gbl.setConstraints(openBtn, gbc);
        gbBtnPanel.add(openBtn);

        gbc.gridx = 2;
        gbc.gridy = 0;
        //gbc.fill=GridBagConstraints.VERTICAL; // 가로/세로 확장모드

        gbl.setConstraints(saveBtn, gbc);
        gbBtnPanel.add(saveBtn);

        gbc.gridx = 3;
        gbc.gridy = 0;
        //gbc.fill=GridBagConstraints.VERTICAL; // 가로/세로 확장모드

        gbl.setConstraints(clipBoardCopyBtn, gbc);
        gbBtnPanel.add(clipBoardCopyBtn);

        gbc.gridx = 4;
        gbc.gridy = 0;
        //gbc.fill=GridBagConstraints.VERTICAL; // 가로/세로 확장모드

        gbl.setConstraints(searchBtn, gbc);
        gbBtnPanel.add(searchBtn);

        gbc.gridx = 5;
        gbc.gridy = 0;
        //gbc.fill=GridBagConstraints.VERTICAL; // 가로/세로 확장모드

        gbl.setConstraints(showBtn, gbc);
        gbBtnPanel.add(showBtn);

        gbc.gridx = 6;
        gbc.gridy = 0;
        //gbc.fill=GridBagConstraints.VERTICAL; // 가로/세로 확장모드

        gbl.setConstraints(execBtn, gbc);
        gbBtnPanel.add(execBtn);

        gbc.gridx = 7;
        gbc.gridy = 0;
        //gbc.fill=GridBagConstraints.VERTICAL; // 가로/세로 확장모드

        gbl.setConstraints(execDescBtn, gbc);
        gbBtnPanel.add(execDescBtn);

        gbc.gridx = 8;
        gbc.gridy = 0;
        //gbc.fill=GridBagConstraints.VERTICAL; // 가로/세로 확장모드

        gbl.setConstraints(statementClearFILEBtn, gbc);
        gbBtnPanel.add(statementClearFILEBtn);
        
        gbl.setConstraints(statementClearSQLBtn, gbc);
        gbBtnPanel.add(statementClearSQLBtn);

        gbc.gridx = 9;
        gbc.gridy = 0;
        //gbc.fill=GridBagConstraints.VERTICAL; // 가로/세로 확장모드

        gbl.setConstraints(exePrcBtn, gbc);
        gbBtnPanel.add(exePrcBtn);

        //문장 구분 라디오 버튼 배치

        radionBtnGroup.add(rbSelect);
        radionBtnGroup.add(rbInsert);
        radionBtnGroup.add(rbUpdate);
        radionBtnGroup.add(rbDelete);
        radionBtnGroup.add(rbSelectDesc);
        radionBtnGroup.add(rbProcSave);
        radionBtnGroup.add(rbProcSel);
        radionBtnGroup.add(rbXmlSave);
        radionBtnGroup.add(rbXmlSel);
        radionBtnGroup.add(rbProcTableTypeVar);

        radionBtnGroup.add(rbProcTypeVarExt);
        radionBtnGroup.add(rbProcTableTypeVarUse);
        radionBtnGroup.add(rbDataSet);
        radionBtnGroup.add(rbColModel);

        radionBtnGroup.setSelected(rbSelect.getModel(), true);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbl.setConstraints(rbSelect, gbc);
        gbBtnPanel.add(rbSelect);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbl.setConstraints(rbInsert, gbc);
        gbBtnPanel.add(rbInsert);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbl.setConstraints(rbUpdate, gbc);
        gbBtnPanel.add(rbUpdate);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbl.setConstraints(rbDelete, gbc);
        gbBtnPanel.add(rbDelete);

        gbc.gridx = 4;
        gbc.gridy = 1;
        gbl.setConstraints(rbSelectDesc, gbc);
        gbBtnPanel.add(rbSelectDesc);

        gbc.gridx = 5;
        gbc.gridy = 1;
        gbl.setConstraints(rbProcSave, gbc);
        gbBtnPanel.add(rbProcSave);

        gbc.gridx = 6;
        gbc.gridy = 1;
        gbl.setConstraints(rbProcSel, gbc);
        gbBtnPanel.add(rbProcSel);

        gbc.gridx = 7;
        gbc.gridy = 1;
        gbl.setConstraints(rbXmlSave, gbc);
        gbBtnPanel.add(rbXmlSave);

        //라디오 버튼
        gbc.gridx = 8;
        gbc.gridy = 1;
        gbl.setConstraints(rbXmlSel, gbc);
        gbBtnPanel.add(rbXmlSel);

        gbc.gridx = 9;
        gbc.gridy = 1;
        gbl.setConstraints(rbProcTableTypeVar, gbc);
        gbBtnPanel.add(rbProcTableTypeVar);

        gbc.gridx = 10;
        gbc.gridy = 1;
        gbl.setConstraints(rbProcTypeVarExt, gbc);
        gbBtnPanel.add(rbProcTypeVarExt);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbl.setConstraints(rbProcTableTypeVarUse, gbc);
        gbBtnPanel.add(rbProcTableTypeVarUse);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbl.setConstraints(rbDataSet, gbc);
        gbBtnPanel.add(rbDataSet);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbl.setConstraints(rbColModel, gbc);
        gbBtnPanel.add(rbColModel);

        //위치조정
        objectTable.setSize(100, 500);
        objectTable.setBounds(0, 0, 200, 800);
        statementFILEArea.setBounds(0, 0, 400, 600);
        statementSQLArea.setBounds(0, 0, 400, 600);

        JPanel Panell = new JPanel(new BorderLayout());

        JPanel ConditionValuePanel = new JPanel(new GridLayout(2, 2));

        ConditionValuePanel.add(dbContComboBox);
        ConditionValuePanel.add(objectComboBox);
        ConditionValuePanel.add(searchTextField);
        ConditionValuePanel.add(dbWorkDivComboBox);

        JScrollPane objectScrollPanel = new JScrollPane(objectTable);
        //JPanel DBConditionPanel2 = new JPanel(new GridLayout(1, 1));
        //DBConditionPanel2.add(objectScrollPanel);

        //좌측 DB탭 영역
        JPanel DBConditionPanel = new JPanel(new BorderLayout());
        DBConditionPanel.add(ConditionValuePanel, BorderLayout.NORTH);
        DBConditionPanel.add(objectScrollPanel, BorderLayout.CENTER);

        conditionLeftTabPanel.addTab("DB", DBConditionPanel);

        //좌측 탑색기탭 영역 디렉토리 그리드 영역
        JPanel directoryCondPanel = new JPanel(new BorderLayout());
        JPanel directoryCondPanel11 = new JPanel(new GridLayout(1, 3));//버튼영역
        directoryCondPanel11.setLayout(new BoxLayout(directoryCondPanel11, BoxLayout.X_AXIS));
        //버튼영역
        directoryCondPanel11.add(maxTopDirMoveBtn);//최상위 이동
        directoryCondPanel11.add(topDirMoveBtn); //상위 이동
        directoryCondPanel11.add(subDirMoveBtn); //서브 이동
        directoryCondPanel11.add(newDirBtn); //신규 폴더
        directoryCondPanel11.add(delDirBtn); //폴더 삭제
        JPanel directoryCondPanel12 = new JPanel(new GridLayout(1, 1));
        directoryCondPanel12.setLayout(new BoxLayout(directoryCondPanel12, BoxLayout.X_AXIS));
        //버튼영역 넣기
        directoryCondPanel.add(directoryCondPanel11, BorderLayout.NORTH);

        directoryCondPanel12.add(tlDir);//폴더명 라벨
        directoryCondPanel12.add(tfDir);//폴더명 텍스트
        //검색값 넣기
        directoryCondPanel.add(directoryCondPanel12, BorderLayout.CENTER);

        JPanel directoryBoarderPanel = new JPanel(new BorderLayout());

        directoryBoarderPanel.add(directoryCondPanel, BorderLayout.NORTH);

        JScrollPane directoryScrollPanel = new JScrollPane(directoryGridTable);//디렉토리 목록
        directoryBoarderPanel.add(directoryScrollPanel, BorderLayout.CENTER);

        JPanel FileSearchConditionPanel = new JPanel(new GridLayout(2, 1));//탐색기 영역

        JPanel ObjectByContentPanel = new JPanel(new BorderLayout());

        FileSearchConditionPanel.add(directoryBoarderPanel);//디렉토리 영역 추가

        //좌측 탑색기탭 파일 그리드 영역

        //파일레이아웃 성정
        JPanel fileBoarderPanel = new JPanel(new BorderLayout());

        JPanel fileCondPanel = new JPanel(new GridLayout(2, 1));
        JPanel fileCondBorder1Panel = new JPanel(new BorderLayout());
        JPanel fileCondBorder2Panel = new JPanel(new GridLayout(1, 4));
        fileCondBorder2Panel.setLayout(new BoxLayout(fileCondBorder2Panel, BoxLayout.X_AXIS));
        jlFileDirPath.setBorder(BorderFactory.createLineBorder(Color.green));

        //상단1
        fileCondBorder1Panel.add(jlFileDirPath, BorderLayout.WEST);//경로라벨
        fileCondBorder1Panel.add(tfFileDirPath, BorderLayout.CENTER);//경로정보

        //상단2
        fileCondBorder2Panel.add(tfFileName); //파일명
        fileCondBorder2Panel.add(newFileBtn); //신규파일
        fileCondBorder2Panel.add(renameFileBtn);//이름변경
        fileCondBorder2Panel.add(delFileBtn); //파일삭제

        fileCondPanel.add(fileCondBorder1Panel);//상단1
        fileCondPanel.add(fileCondBorder2Panel);//상단2

        fileBoarderPanel.add(fileCondPanel, BorderLayout.NORTH);//상단 조건영역 추가

        JScrollPane fileScrollPanel = new JScrollPane(fileGridTable);
        fileBoarderPanel.add(fileScrollPanel, BorderLayout.CENTER);//파일 그리드 추가

        FileSearchConditionPanel.add(fileBoarderPanel);//레이아아웃에 파일 영역 추가

        conditionLeftTabPanel.addTab("탐색기", FileSearchConditionPanel);//탭에 레이아웃 추가

        JPanel objectContentCondPanel = new JPanel(new BorderLayout());
        objectContentCondPanel.add(jlObjectContent, BorderLayout.WEST);//경로라벨
        objectContentCondPanel.add(tfObjectContent, BorderLayout.CENTER);//경로정보
        objectContentCondPanel.add(btnObjectContent, BorderLayout.EAST);//경로정보

        JScrollPane objectContentGridScrollPanel = new JScrollPane(objectContentGridTable);
        ObjectByContentPanel.add(objectContentCondPanel, BorderLayout.NORTH);
        ObjectByContentPanel.add(objectContentGridScrollPanel, BorderLayout.CENTER);
        conditionLeftTabPanel.addTab("내용으로오브젝트검색", ObjectByContentPanel);//탭에 레이아웃 추가

        //우측 문장 탭 영역
        JScrollPane stateMentScrollPanelFILE = new JScrollPane(statementFILEArea);
        JScrollPane stateMentScrollPanelSQL = new JScrollPane(statementSQLArea);
        contentTabPanel.addTab("파일문장", stateMentScrollPanelFILE);
        contentTabPanel.addTab("SQL문장", stateMentScrollPanelSQL);

        //우측 문장실행결과 탭 영역 시작
        JScrollPane resultscrollPanel = new JScrollPane(resultTable);

        contentTabPanel.addTab("실행결과", resultscrollPanel);

        //우측명세 탭 영역
        JScrollPane tableDescScrollPanel = new JScrollPane(descTable);
        JPanel descGridPanel = new JPanel(new GridLayout(2, 1));
        JPanel descBorderPanel1 = new JPanel(new BorderLayout());
        descBorderPanel1.add(jlDescResult, BorderLayout.NORTH);
        descBorderPanel1.add(tableDescScrollPanel, BorderLayout.CENTER);
        JPanel descBorderPanel2 = new JPanel(new BorderLayout());
        descBorderPanel2.add(jlDescStatement, BorderLayout.NORTH);
        JScrollPane descStatementAreaScrollPanel = new JScrollPane(descStatementArea);
        descBorderPanel2.add(descStatementAreaScrollPanel, BorderLayout.CENTER);

        descGridPanel.add(descBorderPanel1);
        descGridPanel.add(descBorderPanel2);

        contentTabPanel.addTab("명세", descGridPanel);

        //우측명세실행결과 탭 영역
        JScrollPane descExecResultScrollPanel = new JScrollPane(descExecResultTable);
        contentTabPanel.addTab("명세실행결과", descExecResultScrollPanel);

        //우측명명 탭 영역
        JPanel gbNamePanel = new JPanel(new GridLayout(1, 9));

        gbNamePanel.add(jl1);
        gbNamePanel.add(tf1);
        gbNamePanel.add(jl2);
        gbNamePanel.add(tf2);
        gbNamePanel.add(jl3);
        gbNamePanel.add(tf3);
        gbNamePanel.add(jl4);
        gbNamePanel.add(tf4);
        //gbNamePanel.add(jl5);
        gbNamePanel.add(nameSearchBtnSub);

        //gbNamePanel.add(jl5);
        JPanel nameListBoarderPanel = new JPanel(new BorderLayout());
        nameListBoarderPanel.add(gbNamePanel, BorderLayout.NORTH);
        JScrollPane nameScrollPanel = new JScrollPane(nameTable);
        nameListBoarderPanel.add(nameScrollPanel, BorderLayout.CENTER);

        contentTabPanel.addTab("명명", nameListBoarderPanel);

        //우측엑셀 탭 영역
        JScrollPane excelScrollPanel = new JScrollPane(excelGridTable);
        contentTabPanel.addTab("엑셀", excelScrollPanel);

        //우측 자동생성 탭 영역 resultProcTable resultProcParamScrollPanel
        JScrollPane jAutoDirEditScrollPanel = new JScrollPane(jAutoDirEditPanel);
        contentTabPanel.addTab("임시메모", jAutoDirEditScrollPanel);

        JPanel resultProcBoarderPanel = new JPanel(new BorderLayout());
        JScrollPane resultProcParamScrollPanel = new JScrollPane(resultProcParamTable);
        JScrollPane resultProcScrollPanel = new JScrollPane(resultProcTable);

        resultProcBoarderPanel.add(resultProcParamScrollPanel, BorderLayout.NORTH);
        resultProcBoarderPanel.add(resultProcScrollPanel, BorderLayout.CENTER);

        contentTabPanel.addTab("프로시저실행", resultProcBoarderPanel);

        Panell.add(gbBtnPanel, BorderLayout.NORTH);

        c.add(Panell, BorderLayout.NORTH);
        c.add(conditionLeftTabPanel, BorderLayout.WEST);
        c.add(contentTabPanel, BorderLayout.CENTER);

        //descTable
        this.setVisible(true);
        //this.setResizable(true);
        this.setBounds(100, 100, 1300, 900);
        KeyStroke ks = null;

        addListeners();


    }

    void addListeners(){
        MyMenuActionListener kl = new MyMenuActionListener();
        //objectTable.addFocusListener(fl);
        //        /this.m11.action(evt, this)
        this.m01.addActionListener(kl);
        this.m02.addActionListener(kl);
        this.m11.addActionListener(kl);
        this.fileSaveMenu.addActionListener(kl);
        this.m13.addActionListener(kl);

        this.copyToClipboardMenu.addActionListener(kl);
        this.clearStatementMenuFILE.addActionListener(kl);
        this.clearStatementMenuSQL.addActionListener(kl);

        this.m31.addActionListener(kl);
        this.m32.addActionListener(kl);
        this.executeStatementMenu.addActionListener(kl);
        this.executeDescMenu.addActionListener(kl);
        this.executeProcMenu.addActionListener(kl);
        //this.menu.addChangeListener(kl);
        //this.menu.addChangeListener((ChangeListener)this);
        MyActionListener mal = new MyActionListener();

        MyChangeListener mcl = new MyChangeListener();

        this.objectComboBox.addActionListener(mal);
        //this.objectComboBox.addItemListener(mcl);
        this.dbWorkDivComboBox.addActionListener(mal);

        this.dbConnBtn.addActionListener(mal);
        this.searchBtn.addActionListener(mal);
        this.showBtn.addActionListener(mal);
        this.openBtn.addActionListener(mal);
        this.saveBtn.addActionListener(mal);
        this.execBtn.addActionListener(mal);
        this.clipBoardCopyBtn.addActionListener(mal);
        this.exePrcBtn.addActionListener(mal);

        this.nameSearchBtnSub.addActionListener(mal);

        this.execDescBtn.addActionListener(mal);
        this.statementClearFILEBtn.addActionListener(mal);
        this.statementClearSQLBtn.addActionListener(mal);
        this.rbSelectDesc.addActionListener(mal);
        this.rbSelect.addActionListener(mal);
        this.rbInsert.addActionListener(mal);
        this.rbUpdate.addActionListener(mal);
        this.rbDelete.addActionListener(mal);

        this.rbProcSave.addActionListener(mal);
        this.rbProcSel.addActionListener(mal);
        this.rbXmlSave.addActionListener(mal);
        this.rbXmlSel.addActionListener(mal);
        this.rbProcTableTypeVar.addActionListener(mal);
        this.rbProcTypeVarExt.addActionListener(mal);
        this.rbProcTableTypeVarUse.addActionListener(mal);
        this.rbDataSet.addActionListener(mal);
        this.rbColModel.addActionListener(mal);

        this.maxTopDirMoveBtn.addActionListener(mal);
        this.topDirMoveBtn.addActionListener(mal);
        this.subDirMoveBtn.addActionListener(mal);
        this.newDirBtn.addActionListener(mal);
        this.delDirBtn.addActionListener(mal);
        this.newFileBtn.addActionListener(mal);
        this.renameFileBtn.addActionListener(mal);
        this.delFileBtn.addActionListener(mal);
        this.btnObjectContent.addActionListener(mal);

        FocusListener fl = new MyFocusListener();
        this.objectComboBox.addFocusListener(fl);
        directoryGridTable.setCellSelectionEnabled(true);

        fileGridTable.setCellSelectionEnabled(true);

        objectTable.setCellSelectionEnabled(true);
        objectCellSelectionModel = objectTable.getSelectionModel();
        objectCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        objectContentGridTable.setCellSelectionEnabled(true);
        objectContentCellSelectionModel = objectContentGridTable.getSelectionModel();
        objectContentCellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        MyListSelectionListener ls = new MyListSelectionListener();
        MyKeyListener kl1 = new MyKeyListener();
        searchTextField.addKeyListener(kl1);
        tfObjectContent.addKeyListener(kl1);
        MyMouseListener ms = new MyMouseListener();

        descTable.addMouseListener(ms);
        directoryGridTable.addMouseListener(ms);
        fileGridTable.addMouseListener(ms);
        //fileGridTable.addMouseListener(ms);

        fileGridTable.setRowSelectionAllowed(true);

        objectCellSelectionModel.addListSelectionListener(ls);
        objectContentCellSelectionModel.addListSelectionListener(ls);
        //statementArea.addKeyListener(kl1);

        String ACTION_KEY = "theAction";

        //saveBtn.setAction(a)
        KeyStroke controlAlt7 = KeyStroke.getKeyStroke("control alt 7");
        InputMap inputMap = saveBtn.getInputMap();
        inputMap.put(controlAlt7, ACTION_KEY);
        ActionMap actionMap = saveBtn.getActionMap();
        //actionMap.put(ACTION_KEY, mal );

        this.addWindowListener(new WindowAdapter() {

            //창닫을때 이베튼
            public void windowClosing(WindowEvent e){
                releaseConnection();
                System.exit(0);
            }
        });

        //this.button.addActionListener(this);
    }

    public class MyChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e){
        // TODO Auto-generated method stub

        }

    }

    public class MyKeyListener implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e){
            // TODO Auto-generated method stub
            //누른 키값 얻어오기
            int keyCode = e.getKeyCode();
            JTextField src = (JTextField)e.getSource();
            //누른 키가 엔터키인경우
            if(keyCode == KeyEvent.VK_ENTER){
                if(src == searchTextField){
                    searchObjList();
                }
                if(src == tfObjectContent){
                    searchObjContentList();
                }
            }
            /*
            if(src==searchTextField ){
                searchObjList();//오브젝트 목록
            }else if(src == tfObjectContent)
                searchObjContentList();//내용으로 오브젝트 검색 목록
            }
            */
        }

        @Override
        public void keyReleased(KeyEvent e){
        // TODO Auto-generated method stub

        }

        @Override
        public void keyTyped(KeyEvent e){
            // TODO Auto-generated method stub
            int keyCode = e.getKeyCode();
            String keyString = "key code = " + keyCode + " (" + KeyEvent.getKeyText(keyCode) + ")";
        }

    }

    public class MyMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e){
            // TODO Auto-generated method stub
            JTable t = (JTable)e.getSource();
            //System.out.println("DblClick2>>>"+e.getClickCount());
            TableModel m = t.getModel();
            if(t == directoryGridTable){
                if(e.getClickCount() == 1){

                    Point pt = e.getPoint();
                    int i = t.rowAtPoint(pt);
                    int c = t.columnAtPoint(pt);

                    if(i >= 0){
                        if(c == 0){
                            int row = t.convertRowIndexToModel(i);

                            String dirName = m.getValueAt(row, 0).toString();

                            String upDir = m.getValueAt(row, 2).toString();

                            String upPath = upDir + File.separator + dirName;

                            tfFileDirPath.setText(upPath);
                            //selDirList(upPath,upDir);
                            selFileList(upPath);
                        }
                        if(c == 2){
                            int row = t.convertRowIndexToModel(i);

                            String upPath = m.getValueAt(row, 3).toString();
                            String upDir = m.getValueAt(row, 2).toString();

                            if(upPath.length() > 3){
                                selDirList(upPath, upDir);
                                selFileList(upPath);
                            }
                        }
                        if(c == 3){
                            int row = t.convertRowIndexToModel(i);

                            String upPath = m.getValueAt(row, 3).toString();
                            String upDir = m.getValueAt(row, 2).toString();

                            if(upPath.length() > 3){
                                selDirList(upPath, upDir);
                                selFileList(upPath);
                            }
                        }
                        if(c < 2){
                            int row = t.convertRowIndexToModel(i);

                            g_SubPath = m.getValueAt(row, 3).toString() + File.separator
                                    + m.getValueAt(row, 0).toString();
                            g_UpPath = m.getValueAt(row, 3).toString();
                            g_UpDir = m.getValueAt(row, 2).toString();
                            //System.out.println("DblClick>>>"+m.getValueAt(row, i));
                        }
                    }
                }
            }else if(t == fileGridTable){
                Point pt = e.getPoint();
                int i = t.rowAtPoint(pt);
                int c = t.columnAtPoint(pt);
                int row = t.convertRowIndexToModel(i);
                String fileName = m.getValueAt(row, 0).toString();
                String filePath = tfFileDirPath.getText();
                filePath = filePath + File.separator + fileName;
                fileByExt(filePath);
            }else if(t == descTable){
                Point pt = e.getPoint();
                int i = t.rowAtPoint(pt);
                int c = t.columnAtPoint(pt);
                int row = t.convertRowIndexToModel(i);
                String val7 = m.getValueAt(row, 7).toString();//본문
                String val8 = m.getValueAt(row, 8).toString();//조건

                if(c == 7){
                    if(val7.equals("")){
                        m.setValueAt("Y", row, 7);
                    }else{
                        m.setValueAt("", row, 7);
                    }
                }
                if(c == 8){
                    if(val8.equals("")){
                        m.setValueAt("Y", row, 8);
                    }else{
                        m.setValueAt("", row, 8);
                    }
                }

            }
        }

        @Override
        public void mouseEntered(MouseEvent e){
        // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e){
        // TODO Auto-generated method stub

        }

        @Override
        public void mousePressed(MouseEvent e){
        // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e){
        // TODO Auto-generated method stub

        }

    }

    public class MyListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e){
            //objectTable.removeAll();
            Object obj = e.getSource();

            String selectedData1 = null;
            String selectedData2 = null;
            int selRow = 0;
            int selCol = 0;
            int[] selectedRow = null;
            int[] selectedColumns = null;
            if(obj == objectCellSelectionModel){
                selRow = objectTable.getSelectedRow();
                selCol = objectTable.getSelectedColumn();
                selectedRow = objectTable.getSelectedRows();
                selectedColumns = objectTable.getSelectedColumns();
            }
            String objName = "";
            String objDiv = "";
            String Comment = "";
            String dirPath = "";
            String filePath = "";

            String contYN = "";
            String condYN = "";
            System.out.println("hello");
            if(obj == objectCellSelectionModel){
                objName = objectTable.getValueAt(selRow, 0).toString();
                Comment = objectTable.getValueAt(selRow, 1).toString();
            }

            if(obj == objectCellSelectionModel){
                tfTableName.setText(objName);
                //lInputTextField2.setText(Comment);
                selStatement(objName);
            }

            if(obj == objectContentCellSelectionModel){
                selRow = objectContentGridTable.getSelectedRow();
                objName = objectContentGridTable.getValueAt(selRow, 0).toString();
                objDiv = objectContentGridTable.getValueAt(selRow, 1).toString();
            }

            if(obj == objectContentCellSelectionModel){
                selStatement(objName, objDiv);
            }

        }

    }

    public class MyFocusListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e){
        // TODO Auto-generated method stub
        //System.out.println("focusGained");

        }

        @Override
        public void focusLost(FocusEvent e){
        // TODO Auto-generated method stub

        }

    }

    //메뉴 선택시
    public class MyMenuActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            Object o = e.getSource();
            if(o == m11){
                openFile();
            }else if(o == fileSaveMenu){
                saveToFile();
            }else if(o == m13){
                releaseConnection();
                System.exit(0);
            }else if(o == copyToClipboardMenu){
                copyToClipboard();
            }else if(o == clearStatementMenuFILE){
                statementFILEArea.setText("");
            }else if(o == clearStatementMenuSQL){
                statementSQLArea.setText("");
            }else if(o == m31){
                searchObjList();

            }else if(o == m32){
                selStatement();
            }else if(o == executeStatementMenu){
                execStatement();
            }else if(o == executeDescMenu){
                execDescStatement();
            }else if(o == executeProcMenu){
                exePrc();
            }

        }

    }

    public class MyActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e){
            Object obj = e.getSource();

            if(obj == m01){//개발DB접속

                getConnection("DEV");
                //cm.setConn(conn);

            }else if(obj == m02){//테스트DB접속

                getConnection("개발");
                //cm.setConn(conn);
            }else if(obj == dbConnBtn){//DB연결
                connDB();
            }else if(obj == searchBtn){//오브젝트 조회 버튼
                searchObjList();
            }else if(obj == showBtn){//문장조회 버튼
                selStatement();
            }else if(obj == saveBtn){

                saveToFile();
            }else if(obj == openBtn){//open버튼
                openFile();
            }else if(obj == execBtn){//실행버튼
                execStatement();
            }else if(obj == execDescBtn){//명세실행버튼
                execDescStatement();
            }else if(obj == statementClearFILEBtn){//편집창(FILE문장) 클리어 버튼
                statementFILEArea.setText("");
            }else if(obj == statementClearSQLBtn){//편집창(SQL문장) 클리어 버튼
                statementSQLArea.setText("");
            }else if(obj == clipBoardCopyBtn){//클립보드로 복사 버튼
                copyToClipboard();
            }else if(obj == exePrcBtn){//프로시저 실행
                exePrc();
            }else if(obj == nameSearchBtnSub){//명명리스트 조회 버튼
                searchNameList();//임시
            }else if(obj == maxTopDirMoveBtn){//최상위 이동 버튼
                moveDir("MAX_TOP_DIR");
            }else if(obj == topDirMoveBtn){//상위 이동 버튼
                moveDir("TOP_DIR");
            }else if(obj == subDirMoveBtn){//상위 이동 버튼
                moveDir("SUB_DIR");
            }else if(obj == newDirBtn){//신규폴더 버튼
                newDir();
            }else if(obj == delDirBtn){//폴더삭제 버튼
                delDir();
            }else if(obj == newFileBtn){//신규파일 버튼
                newFile();
            }else if(obj == renameFileBtn){//파일이름변경 버튼
                renameFile();
            }else if(obj == delFileBtn){//파일삭제 버튼
                delFile();
            }else if(obj == btnObjectContent){//내용으로 오브젝트 검색 버튼
                searchObjContentList();
            }else if(obj == button){
                popup.hide();
            }else if(obj == objectComboBox){
                String objDiv = objectComboBox.getSelectedItem().toString();
                String workDiv = dbWorkDivComboBox.getSelectedItem().toString();
                String objName = searchTextField.getText();

                if(objDiv.equals("TABLE")){
                    cm.makeData(objName);

                }else{
                    cm.makeDataExt(objName, objDiv);
                }
                selObjList(objDiv, objName.trim().toUpperCase(), workDiv);
            }else if(obj == dbWorkDivComboBox){
                String objDiv = objectComboBox.getSelectedItem().toString();
                String workDiv = dbWorkDivComboBox.getSelectedItem().toString();
                String objName = searchTextField.getText();

                if(objDiv.equals("TABLE")){
                    cm.makeData(objName);
                }else{
                    cm.makeDataExt(objName, objDiv);
                }
                selObjList(objDiv, objName.trim().toUpperCase(), workDiv);
            }else if(obj == rbSelectDesc){
                gSelRadio = selRadioStatemet("rbSelectDesc");
            }else if(obj == rbSelect){
                gSelRadio = selRadioStatemet("rbSelect");
            }else if(obj == rbInsert){
                gSelRadio = selRadioStatemet("rbInsert");
            }else if(obj == rbUpdate){
                gSelRadio = selRadioStatemet("rbUpdate");
            }else if(obj == rbDelete){
                gSelRadio = selRadioStatemet("rbDelete");
            }else if(obj == rbProcSave){
                gSelRadio = selRadioStatemet("rbProcSave");
            }else if(obj == rbProcSel){
                gSelRadio = selRadioStatemet("rbProcSel");
            }else if(obj == rbXmlSave){
                gSelRadio = selRadioStatemet("rbXmlSave");
            }else if(obj == rbXmlSel){
                gSelRadio = selRadioStatemet("rbXmlSel");

            }else if(obj == rbProcTableTypeVar){
                gSelRadio = selRadioStatemet("rbProcTableTypeVar");
            }else if(obj == rbProcTypeVarExt){
                gSelRadio = selRadioStatemet("rbProcTypeVarExt");
            }else if(obj == rbProcTableTypeVarUse){
                gSelRadio = selRadioStatemet("rbProcTableTypeVarUse");
            }else if(obj == rbDataSet){
                gSelRadio = selRadioStatemet("rbDataSet");
            }else if(obj == rbColModel){
                gSelRadio = selRadioStatemet("rbColModel");
            }
        }
    }

    //문장 보여주기
    private void selStatement(String objectName){
        //System.out.println(tfTableName.getText() + "," + lInputTextField2.getText() + ","
        //+ classListComboBox.getSelectedItem() + "|||");
        ArrayList resultAL = new ArrayList();
        cm.setAddData(lInputTextField3.getText(), lInputTextField4.getText());
        String objDiv = objectComboBox.getSelectedItem().toString();
        //objDiv="TABLE";

        if(objDiv.equals("TABLE")){

            //System.out.println("objectName1>>" + objectName);
            HashMap sumMap = cm.makeData(objectName);
            //cm.execProc("TEST");

            resultAL = cm.readData(gSelRadio);//
            setDescTable(sumMap);
        }else{
            cm.makeDataExt(objectName, objDiv);
            if(objDiv.equals("VIEW")){
                resultAL = cm.readData("VIEW_DDL");//
            }else{

                resultAL = cm.readData("DDL");//
                if(objDiv.equals("PROCEDURE")){
                    ArrayList result2 = new ArrayList();

                    result2 = cm.execProcParam(objectName);
                    setResultProcParamTable(result2);
                }
            }
        }
        if(objDiv.equals("VIEW")){
            System.out.println("aaa>>" + resultAL.size());
            statementSQLArea.setText(((HashMap)resultAL.get(0)).get("COMMENTS").toString());
            
        }else{
            setOutput("COLLECTION", resultAL);
        }
    }

    //문장 보여주기
    private void selStatement(String objectName, String objDiv){
        //System.out.println(tfTableName.getText() + "," + lInputTextField2.getText() + ","
        //+ classListComboBox.getSelectedItem() + "|||");
        ArrayList resultAL = new ArrayList();

        //objDiv="TABLE";

        cm.makeDataExt(objectName, objDiv);

        resultAL = cm.readData("DDL");//
        if(objDiv.equals("PROCEDURE")){
            ArrayList result2 = new ArrayList();

            result2 = cm.execProcParam(objectName);
            setResultProcParamTable(result2);
        }

        setOutput("COLLECTION", resultAL);

    }

    //문장 결과텍스트 arer에 출력
    private void setOutput(String source, ArrayList resultAL){
        if(source.equals("FILE")){
            String path = "E:/backup/a.txt";
            statementFILEArea.setText("");
            int rows = statementFILEArea.getRows();

            try{
                BufferedReader in = new BufferedReader(new FileReader(path));
                String str = "";
                String line = null;
                int i = 0;
                while((line = in.readLine()) != null){
                    statementFILEArea.append(line);
                    i++;
                }
                in.close();
            }catch(IOException e2){
                // TODO Auto-generated catch block
                //throw new LException(e);
            }
        }else if(source.equals("COLLECTION")){
            //System.out.println("resultAL.size()>>"+resultAL.size());
            String path = "E:/backup/a.txt";
            statementSQLArea.setText("");
            int rows = statementSQLArea.getRows();
            String objDiv = objectComboBox.getSelectedItem().toString();
            if(objDiv.equals("TABLE")){
                for(int i = 0; i < resultAL.size(); i++){
                    statementSQLArea.append(resultAL.get(i) + "\n");
                }
            }else{
                for(int i = 0; i < resultAL.size(); i++){
                    statementSQLArea.append(resultAL.get(i) + "");
                }
            }
            
            contentTabPanel.setSelectedIndex(1);

        }
    }

    //테이블 목록 보여주기
    private void selObjList(String objDiv, String objName, String workDiv){
        //String tableName = "";
        cm.setAddData(lInputTextField3.getText(), lInputTextField4.getText());
        //System.out.println("objDiv>>" + objDiv);
        ArrayList objectList = cm.makeDataObject(objDiv, objName.trim(), workDiv);
        //cm.readData("selectObjList");//

        //String path = "D:/backup/a.txt";

        setObjectTable(objectList);
        contentTabPanel.setSelectedIndex(1);

    }

    //내용으로 오브젝트 검색 목록 보여주기
    private void selObjContentList(String objName){
        //String tableName = "";
        //cm.setAddData(lInputTextField3.getText(), lInputTextField4.getText());
        System.out.println("objName>>" + objName);
        ArrayList objectList = cm.makeDataObjectContent(objName);
        System.out.println("objectList>>" + objectList.size());
        setObjectContentTable(objectList);

    }

    //디렉토리  목록 보여주기
    private void selDirList(String dirPath, String upDir){
        ApiTest at = new ApiTest();
        ArrayList resultList = at.getDirFileList(dirPath, "DIR");
        HashMap dirMap = new HashMap();
        for(int i = 0; i < resultList.size(); i++){
            dirMap = (HashMap)resultList.get(i);
        }
        //directoryGridDtm.setRowCount(0);
        setDirectoryGridTable(resultList, dirPath, upDir);
    }

    //파일  목록 보여주기
    private void selFileList(String dirPath){
        ApiTest at = new ApiTest();
        ArrayList resultList = at.getDirFileList(dirPath, "FILE");
        HashMap fileMap = new HashMap();
        for(int i = 0; i < resultList.size(); i++){
            fileMap = (HashMap)resultList.get(i);
        }
        setFileGridTable(resultList);
    }

    //테이블 목록 보여주기
    private void selNameList(String methodWork2, String file_work_div, String workDiv, String s_workDiv,
            String work_k_name){
        //String tableName = "";
        cm.setAddData(lInputTextField3.getText(), lInputTextField4.getText());
        //System.out.println("objDiresultVector>>" + methodWork2);

        ArrayList nameList = cm.makeNameData(methodWork2, file_work_div, workDiv, s_workDiv, work_k_name);
        //cm.readData("selectObjList");//

        //String path = "D:/backup/a.txt";
        //System.out.println("nameList>>" + nameList.size());
        setNameTable(nameList);

    }

    //라디오 버튼 선택 처리
    private String selRadioStatemet(String pSelRadio){
        String selRadio = "";
        Enumeration elements = radionBtnGroup.getElements();
        while(elements.hasMoreElements()){
            AbstractButton button = (AbstractButton)elements.nextElement();
            if(button.isSelected()){
                System.out.println("The winner is: " + button.getText());
                selRadio = button.getText();
            }
        }

        //tfTableName.getText();
        gSelRadio = selRadio;
        String tbName = tfTableName.getText();
        selStatement(tbName.trim());
        return selRadio;
    }

    //프로시저 실행  결과  보여주기
    private void exePrc(){
        //String tableName = "";
        //cm.setAddData(lInputTextField3.getText(), lInputTextField4.getText());
        int selRow = objectTable.getSelectedRow();
        System.out.println("exePrc" + selRow);
        String objName = objectTable.getValueAt(selRow, 0).toString();

        int rowCnt = resultProcParamTable.getRowCount();
        StringBuffer sb = new StringBuffer();
        int tInx = rowCnt - 1;
        sb.append("{CALL " + objName + "(");
        String NUM = "";
        String PARAM = "";
        String IN_OUT = "";
        String VAL = "";
        HashMap hm = null;
        ArrayList al = new ArrayList();
        for(int i = 0; i < rowCnt; i++){
            hm = new HashMap();
            NUM = resultProcParamTable.getValueAt(i, 0).toString();
            PARAM = resultProcParamTable.getValueAt(i, 1).toString();
            IN_OUT = resultProcParamTable.getValueAt(i, 2).toString();
            VAL = resultProcParamTable.getValueAt(i, 4).toString();
            hm.put("NUM", NUM);
            hm.put("PARAM", PARAM);
            hm.put("IN_OUT", IN_OUT);
            hm.put("VAL", VAL);
            if(i == tInx){
                sb.append("?)}");
            }else{
                sb.append("?,");
            }
            al.add(hm);
        }
        String prcStr = sb.toString();
        HashMap resultProcMap = cm.execProc(prcStr, al);

        setResultProcTable(resultProcMap);

    }

    /**
     * 오브젝트 목록을 조회한다.
     */
    public void setObjectTable(ArrayList objectList){
        try{
            objectDtm.setRowCount(0);
            //Table 행 전체 지우기
            int deleteInx = 0;
            int m = 0;

            String str = "";
            String data = null;
            String tblComment = "";
            String author = "";
            int objectCnt = objectList.size();
            int j = 0;
            //while((data = in.readLine()) != null){
            int inx = 0;
            for(j = 0; j < objectCnt; j++){

                HashMap hm2 = (HashMap)objectList.get(j);
                data = hm2.get("TABLE_NAME").toString();
                tblComment = hm2.get("TABLE_COMMENT").toString();
                author = hm2.get("AUTHOR").toString();

                objectVector = new Vector();
                objectVector.add(data);
                objectVector.add(tblComment);
                //objectVector.add(author);
                objectDtm.insertRow(j, objectVector);

                //j++;
                //objectCnt++;
            }

            //in.close();

        }catch(Exception e){
            // TODO Auto-generated catch block
            //throw new LException(e);
        }
    }

    /**
     * 오브젝트 목록을 조회한다.
     */
    public void setObjectContentTable(ArrayList objectList){
        try{
            objectContentGridDtm.setRowCount(0);
            //Table 행 전체 지우기

            String str = "";
            String data = null;
            String tblComment = "";
            int objectCnt = objectList.size();
            int j = 0;
            //while((data = in.readLine()) != null){
            int inx = 0;
            for(j = 0; j < objectCnt; j++){

                HashMap hm2 = (HashMap)objectList.get(j);
                data = hm2.get("COL").toString();
                tblComment = hm2.get("COMMENTS").toString();

                objectContentGridVector = new Vector();
                objectContentGridVector.add(data);
                objectContentGridVector.add(tblComment);
                //objectVector.add(author);
                objectContentGridDtm.insertRow(j, objectContentGridVector);

                //j++;
                //objectCnt++;
            }

            //in.close();

        }catch(Exception e){
            // TODO Auto-generated catch block
            //throw new LException(e);
        }
    }

    /**
     * 디렉토리 목록을 보여준다.
     */
    public void setDirectoryGridTable(ArrayList dirList, String upPath, String upDir2){
        try{
            int dirListCnt = dirList.size();
            //if(directoryGridDtm.getRowCount()>0){
            //System.out.println("dirListCnt222222>>>" + dirListCnt);
            //directoryGridDtm = new DefaultTableModel();

            directoryGridDtm.setRowCount(0);
            //directoryGridDtm.setR
            //System.out.println("dirListCnt333333>>>");
            //}
            //Table 행 전체 지우기
            int deleteInx = 0;
            int m = 0;

            String str = "";
            String dirName = null;
            String upDirName = null;
            String upDir = null;
            String data = null;
            String tblComment = "";
            int j = 0;
            //while((data = in.readLine()) != null){
            int inx = 0;
            if(dirListCnt > 0){

                for(j = 0; j < dirListCnt; j++){
                    //System.out.println("size211>>>" + upPath + "    " + upDir);
                    HashMap hm2 = (HashMap)dirList.get(j);
                    dirName = hm2.get("NAME").toString();
                    upDirName = hm2.get("UP_NAME").toString();
                    upDir = hm2.get("UP_DIR").toString();

                    directoryGridVector = new Vector();
                    directoryGridVector.add(dirName);
                    directoryGridVector.add(hm2.get("COUNT").toString());
                    directoryGridVector.add(upDirName);
                    directoryGridVector.add(upDir);
                    directoryGridDtm.insertRow(j, directoryGridVector);
                    //System.out.println("size233>>>" + upPath + "    " + upDir);
                    if(j == 0){
                        tfFileDirPath.setText(upDirName);
                    }
                }

            }

            else{

                directoryGridVector = new Vector();
                directoryGridVector.add("../");
                directoryGridVector.add(0);
                directoryGridVector.add(upPath);
                directoryGridVector.add(upDir2);
                directoryGridDtm.insertRow(0, directoryGridVector);

                tfFileDirPath.setText(upPath);
            }
            directoryGridTable.changeSelection(0, 0, false, false);
            //in.close();

        }catch(Exception e){
            // TODO Auto-generated catch block
            //System.out.println("  "+e.toString());
            //throw new LException(e);
        }
    }

    /**
     * 파일 목록을 보여준다.
     */
    public void setFileGridTable(ArrayList dirList){
        try{
            fileGridDtm.setRowCount(0);
            //Table 행 전체 지우기

            String str = "";
            String fileName = null;
            String fileSize = null;
            String modifiedTime = null;
            String fileExt = null;
            int dirListCnt = dirList.size();
            int j = 0;
            //while((data = in.readLine()) != null){
            int inx = 0;
            for(j = 0; j < dirListCnt; j++){

                HashMap hm2 = (HashMap)dirList.get(j);
                fileName = hm2.get("NAME").toString();
                fileSize = hm2.get("SIZE").toString();
                modifiedTime = hm2.get("MODIFIED_TIME").toString();
                fileExt = hm2.get("FILE_EXT").toString();
                fileGridVector = new Vector();
                fileGridVector.add(fileName);
                fileGridVector.add(fileSize);
                fileGridVector.add(modifiedTime);
                fileGridVector.add(fileExt);
                fileGridDtm.insertRow(j, fileGridVector);
            }
            fileGridTable.changeSelection(0, 0, false, false);
            //fileGridTable

        }catch(Exception e){
            System.out.println("bbbb>>" + e.toString());
            //throw new LException(e);
        }
    }

    /**
     * 문장 실행 결과를 조회한다.
     */
    public void setResultTable(HashMap sumMap){
        try{
            resultDtm.setColumnCount(0);
            resultDtm.setRowCount(0);
            //resultTable = new JTable(resultDtm, header1);
            HashMap titleMap = new HashMap();
            ArrayList resultList = new ArrayList();
            titleMap = (HashMap)sumMap.get("Title");
            resultList = (ArrayList)sumMap.get("Data");

            String str = "";
            String data = null;

            //데이터심기
            int dataRowCnt = resultList.size();
            int dataCnt = 0;
            int resultCnt = dataRowCnt;
            System.out.println("resultCnt>>" + resultCnt);
            int j = 0;
            //int k = 1;
            int k2 = 1;
            HashMap hm = new HashMap();
            //resultDtm.
            String title = "";
            while(true){
                //k = 1;

                hm = (HashMap)resultList.get(j);
                dataCnt = titleMap.size();
                if(j == 0){
                    resultVector = new Vector();
                    System.out.println("resultCnt2>>" + resultCnt);
                    for(k2 = 1; k2 <= dataCnt; k2++){
                        title = titleMap.get(k2).toString();
                        //resultVector.add(title);
                        resultDtm.addColumn(title, resultVector);
                        //resultTable.getC
                    }

                }
                System.out.println("resultCnt3>>" + resultCnt);
                System.out.println("dataCnt>>" + dataCnt);
                resultVector = new Vector();
                int index = 0;
                for(int k = 1; k <= dataCnt; k++){
                    //index = k - 1;
                    //System.out.println("resultCnt4>>"+resultCnt);
                    data = hm.get(k).toString();
                    //System.out.println("resultCnt6>>"+resultCnt);
                    //System.out.println(j +">" + k + ":" + data);
                    resultVector.add(data);
                    //if(k == dataCnt){

                    //}
                }
                resultDtm.insertRow(j, resultVector);

                System.out.println("resultCnt8>>" + resultCnt);
                if(j == (resultCnt - 1)){
                    System.out.println("resultCnt9>>" + resultCnt);
                    break;
                }
                j++;

            }
            contentTabPanel.setTitleAt(2, "결과 " + dataRowCnt + "건");
            System.out.println("resultCnt11>>" + resultCnt);
            resultTable = new JTable(resultDtm, resultTcm);

            contentTabPanel.setSelectedIndex(2);

            //resultTable.removeColumn(resultTable.getColumnModel().getColumn(1));

        }catch(Exception e){
            System.out.println("Exception:" + e.toString());
            //throw new LException(e);
        }
    }

    /**
     * 명세 실행 결과를 조회한다.
     */
    public void setDescExecResultTable(HashMap sumMap){
        try{
            descExecResultDtm.setColumnCount(0);
            descExecResultDtm.setRowCount(0);
            //resultTable = new JTable(resultDtm, header1);
            HashMap titleMap = new HashMap();
            ArrayList resultList = new ArrayList();
            titleMap = (HashMap)sumMap.get("Title");
            resultList = (ArrayList)sumMap.get("Data");

            String str = "";
            String data = null;

            //데이터심기
            int dataRowCnt = resultList.size();
            int dataCnt = 0;
            int resultCnt = dataRowCnt;
            //System.out.println("resultCnt>>"+resultCnt);
            int j = 0;
            int k = 1;
            int k2 = 1;
            HashMap hm = new HashMap();
            //resultDtm.
            String title = "";
            while(true){
                k = 1;
                hm = (HashMap)resultList.get(j);
                dataCnt = hm.size();
                if(j == 0){
                    for(k2 = 1; k2 <= dataCnt; k2++){
                        title = titleMap.get(k2).toString();
                        descExecResultDtm.addColumn(title, descExecResultVector);
                        //resultTable.getC
                    }

                }
                descExecResultVector = new Vector();
                int index = 0;
                for(k = 1; k <= dataCnt; k++){
                    index = k - 1;
                    data = hm.get(k).toString();
                    //System.out.println(j +">" + k + ":" + data);
                    descExecResultVector.add(data);
                    if(k == dataCnt){
                        descExecResultDtm.insertRow(j, descExecResultVector);
                    }
                }

                if(j == (resultCnt - 1)){
                    break;
                }
                j++;
            }
            contentTabPanel.setTitleAt(4, "명세실행결과 " + dataRowCnt + "건");
            descExecResultTable = new JTable(descExecResultDtm, descExecResultTcm);
            contentTabPanel.setSelectedIndex(4);
            //resultTable.removeColumn(resultTable.getColumnModel().getColumn(1));

        }catch(Exception e){
            // TODO Auto-generated catch block
            //throw new LException(e);
        }
    }

    /**
     * 테이블 명세를 조회한다.
     */
    /*
    public void setDescTable2(HashMap sumMap){
        try{
            descDtm.setColumnCount(0);
            descDtm.setRowCount(0);
            //resultTable = new JTable(resultDtm, header1);

            HashMap titleMap = new HashMap();
            ArrayList resultList = new ArrayList();
            titleMap = (HashMap)sumMap.get("Title");
            resultList = (ArrayList)sumMap.get("Data");

            //Table 행 전체 지우기
            String str = "";
            Object data = null;

            //데이터심기
            int dataRowCnt = resultList.size();
            int dataCnt = 0;
            int resultCnt = dataRowCnt;

            int j = 0;
            int k = 1;
            int k2 = 1;
            HashMap hm = new HashMap();
            //resultDtm.
            String title = "";
            List list = new ArrayList();
            List list2 = new ArrayList();
            List list3 = new ArrayList();
            List list4 = new ArrayList();
            List list5 = new ArrayList();
            List list6 = new ArrayList();
            List list7 = new ArrayList();
            List list8 = new ArrayList();
            List list9 = new ArrayList();
            List list10 = new ArrayList();
            List list11 = new ArrayList();
            int inx1 = 0;
            Object[] col1 = null;
            Object[] col2 = null;
            Object[] col3 = null;
            Object[] col4 = null;
            Object[] col5 = null;
            Object[] col6 = null;
            Object[] col7 = null;
            Object[] col8 = null;
            Object[] col9 = null;
            Object[] col10 = null;
            Object[] col11 = null;
            for(j = 0; j < dataRowCnt; j++){
                hm = (HashMap)resultList.get(j);
                dataCnt = hm.size() + 3;
                k = 1;
                for(k = 1; k <= dataCnt; k++){
                    //list.add(0, Boolean.TRUE);
                    data = hm.get(k);
                    inx1 = k - 1;
                    if(k == 1){
                        list.add(j, data);
                    }
                    if(k == 2){
                        list2.add(j, data);
                    }
                    if(k == 3){
                        list3.add(j, data);
                    }
                    if(k == 4){
                        list4.add(j, data);
                    }
                    if(k == 5){
                        list5.add(j, data);
                    }
                    if(k == 6){
                        list6.add(j, data);
                    }
                    if(k == 7){
                        list7.add(j, data);
                    }
                    if(k == 8){
                        list8.add(j, data);
                    }
                    if(k == 9){
                        list9.add(j, "");
                    }
                    if(k == 10){
                        list10.add(j, "");
                    }
                    if(k == 11){
                        list11.add(j, "");
                    }
                }

            }
            col1 = list.toArray();
            col2 = list2.toArray();
            col3 = list3.toArray();
            col4 = list4.toArray();
            col5 = list5.toArray();
            col6 = list6.toArray();
            col7 = list7.toArray();
            col8 = list8.toArray();
            col9 = list9.toArray();
            col10 = list10.toArray();
            col11 = list11.toArray();

            descDtm.addColumn("Num", col1);
            descDtm.addColumn("  컬럼    ", col2);
            //descDtm.addColumn("Num2", col3);
            descDtm.addColumn("  변수    ", col4);
            descDtm.addColumn("     주석     ", col5);
            descDtm.addColumn("타입", col6);
            descDtm.addColumn("길이", col7);
            descDtm.addColumn("정밀도", col8);
            descDtm.addColumn("본문", col9);
            descDtm.addColumn("조건", col10);
            descDtm.addColumn("조건값", col11);

            contentTabPanel.setTitleAt(2, "명세 " + dataRowCnt + "건");
        }catch(Exception e){
            // TODO Auto-generated catch block
            //throw new LException(e);
        }
    }
    */
    /**
     * 텍스트 파일을 읽어 텍스트 내용을 반환 한다.
     */
    public void setExcelTable(HashMap sumMap){
        try{
            excelDtm.setColumnCount(0);
            excelDtm.setRowCount(0);
            //resultTable = new JTable(resultDtm, header1);
            HashMap titleMap = new HashMap();
            ArrayList resultList = new ArrayList();
            //titleMap = (HashMap)sumMap.get("Title");
            //resultList = (ArrayList)sumMap.get("Data");

            String str = "";
            String data = null;
            int sheetCnt = sumMap.size();
            sheetCnt = 1;
            //데이터심기
            for(int sheetInx = 0; sheetInx < sheetCnt; sheetInx++){
                resultList = (ArrayList)sumMap.get(sheetInx);
                int dataRowCnt = resultList.size();
                int dataCnt = 0;
                int resultCnt = dataRowCnt;

                HashMap hm = new HashMap();
                //resultDtm.
                String title = "";
                for(int row_inx = 0; row_inx < resultCnt; row_inx++){
                    hm = (HashMap)resultList.get(row_inx);
                    dataCnt = hm.size();
                    //dataCnt = 5;
                    if(row_inx == 0){
                        for(int colInx = 0; colInx < dataCnt; colInx++){
                            //title = titleMap.get(colInx).toString();
                            excelDtm.addColumn("       COL" + colInx + "     ", excelVector);
                            //resultTable.getC
                        }

                    }

                    excelVector = new Vector();
                    int index = 0;
                    //System.out.print(row_inx + "> ");
                    for(int colInx = 0; colInx < dataCnt; colInx++){
                        //index = k - 1;
                        data = hm.get(colInx).toString();
                        //System.out.print(data + " ");
                        excelVector.add(data);
                    }
                    excelDtm.insertRow(row_inx, excelVector);
                }
            }
            //contentTabPanel.setTitleAt(4, "엑셀 " + dataRowCnt + "건");
            excelGridTable = new JTable(excelDtm, excelGridTcm);

            //resultTable.removeColumn(resultTable.getColumnModel().getColumn(1));

        }catch(Exception e){
            // TODO Auto-generated catch block
            //throw new LException(e);
        }
    }

    /**
     * 명명 규칙을 조회한다.
     */
    public void setDescTable(HashMap sumMap){
        //public void setNameTable(ArrayList nameList){
        try{
            descDtm.setRowCount(0); //임시
            //Table 행 전체 지우기
            int deleteInx = 0;
            int m = 0;

            ArrayList nameList = new ArrayList();
            //titleMap = (HashMap)sumMap.get("Title");
            nameList = (ArrayList)sumMap.get("Data");

            String str = "";
            String divNm = null;
            String sDivNm = null;
            String srcFileName = "";
            String methodKName = "";
            String methodEName = "";
            int nameCnt = nameList.size();
            int j = 0;
            //while((data = in.readLine()) != null){
            int inx = 0;

            for(j = 0; j < nameCnt; j++){
                HashMap hm = (HashMap)nameList.get(j);
                //HashMap hm2 = (HashMap)hm.get(j);
                //divNm = hm2.get(1).toString();

                descVector = new Vector();

                descVector.add(hm.get(1));

                descVector.add(hm.get(3));
                descVector.add(hm.get(4));
                descVector.add(hm.get(5));
                descVector.add(hm.get(6));

                descVector.add(hm.get(7));
                descVector.add(hm.get(8));
                descVector.add("");
                descVector.add("");
                descVector.add("");
                /*
                Object rowData1[]=new Object[] { Boolean.FALSE };
                descDtm.addColumn("순번", new Object[] { Boolean.FALSE });
                descDtm.insertRow(0, rowData1);
                descDtm.insertRow(j, new Object[] { Boolean.FALSE });
                */

                descDtm.insertRow(j, descVector);

                //j++;
                //objectCnt++;
            }

            //in.close();

        }catch(Exception e){
            System.out.println("nameListException>>>" + e.toString());
        }
    }

    /**
     * 명명 규칙을 조회한다.
     */
    public void setNameTable(ArrayList nameList){
        try{
            nameDtm.setRowCount(0);
            //Table 행 전체 지우기
            int deleteInx = 0;
            int m = 0;

            String str = "";
            String divNm = null;
            String sDivNm = null;
            String srcFileName = "";
            String methodKName = "";
            String methodEName = "";
            int nameCnt = nameList.size();
            int j = 0;
            //while((data = in.readLine()) != null){
            int inx = 0;
            for(j = 0; j < nameCnt; j++){

                HashMap hm2 = (HashMap)nameList.get(j);
                divNm = hm2.get("DIV_NM").toString();
                sDivNm = hm2.get("S_DIV_NM").toString();
                srcFileName = hm2.get("SRC_FILE_NAME").toString();
                methodKName = hm2.get("METHOD_K_NAME").toString();
                methodEName = hm2.get("METHOD_E_NAME").toString();

                nameVector = new Vector();
                nameVector.add(divNm);
                nameVector.add(sDivNm);
                nameVector.add(srcFileName);
                nameVector.add(methodKName);
                nameVector.add(methodEName);
                nameDtm.insertRow(j, nameVector);

                //j++;
                //objectCnt++;
            }

            //in.close();

        }catch(Exception e){
            // TODO Auto-generated catch block
            //throw new LException(e);
        }
    }

    /**
     * 오브젝트 목록을 조회한다.
     */
    public void setResultProcParamTable(ArrayList objectList){
        try{
            resultProcParamDtm.setRowCount(0);
            //Table 행 전체 지우기
            int deleteInx = 0;
            int m = 0;

            String str = "";
            String RN = null;
            String ARGUMENT_NAME = null;
            String IN_OUT = "";
            String DATA_TYPE = null;

            int objectCnt = objectList.size();
            int j = 0;
            //while((data = in.readLine()) != null){
            int inx = 0;
            for(j = 0; j < objectCnt; j++){

                HashMap hm2 = (HashMap)objectList.get(j);
                RN = hm2.get("RN").toString();
                ARGUMENT_NAME = hm2.get("ARGUMENT_NAME").toString();
                IN_OUT = hm2.get("IN_OUT").toString();
                DATA_TYPE = hm2.get("DATA_TYPE").toString();

                resultProcParamVector = new Vector();
                resultProcParamVector.add(RN);
                resultProcParamVector.add(ARGUMENT_NAME);
                resultProcParamVector.add(IN_OUT);
                resultProcParamVector.add(DATA_TYPE);
                resultProcParamVector.add("");
                resultProcParamDtm.insertRow(j, resultProcParamVector);

                //j++;
                //objectCnt++;
            }

            //in.close();

        }catch(Exception e){
            // TODO Auto-generated catch block
            //throw new LException(e);
        }
    }

    /**
     * 프로시저 실행 결과를 조회한다.
     */
    public void setResultProcTable(HashMap sumMap){
        try{
            resultProcDtm.setColumnCount(0);
            resultProcDtm.setRowCount(0);
            //resultTable = new JTable(resultDtm, header1);
            HashMap titleMap = new HashMap();
            ArrayList resultList = new ArrayList();
            titleMap = (HashMap)sumMap.get("Title");
            resultList = (ArrayList)sumMap.get("Data");

            String str = "";
            String data = null;

            //데이터심기
            int dataRowCnt = resultList.size();
            int dataCnt = 0;
            int resultCnt = dataRowCnt;
            int j = 0;
            //int k = 1;
            int k2 = 1;
            HashMap hm = new HashMap();
            //resultDtm.
            String title = "";
            while(true){
                //k = 1;

                hm = (HashMap)resultList.get(j);
                dataCnt = titleMap.size();
                if(j == 0){
                    resultProcVector = new Vector();
                    for(k2 = 1; k2 <= dataCnt; k2++){
                        title = titleMap.get(k2).toString();
                        //resultVector.add(title);
                        resultProcDtm.addColumn(title, resultProcVector);
                        //resultTable.getC
                    }

                }
                resultProcVector = new Vector();
                int index = 0;
                for(int k = 1; k <= dataCnt; k++){
                    //index = k - 1;
                    data = hm.get(k).toString();
                    resultProcVector.add(data);
                    //if(k == dataCnt){

                    //}
                }
                resultProcDtm.insertRow(j, resultProcVector);

                if(j == (resultCnt - 1)){

                    break;
                }
                j++;

            }
            contentTabPanel.setTitleAt(7, "결과 " + dataRowCnt + "건");
            resultProcTable = new JTable(resultProcDtm, resultProcTcm);

            //contentTabPanel.setSelectedIndex(1);

            //resultTable.removeColumn(resultTable.getColumnModel().getColumn(1));

        }catch(Exception e){
            System.out.println("Exception:" + e.toString());
            //throw new LException(e);
        }
    }

    //파일읽기
    public void fileRead(String path){
        //System.out.println("파일읽기 시작>>>" + path);
        try{
            //jAutoDirEditPanel.getT
            //JOptionPane.showMessageDialog(jAutoDirEditPanel, jAutoDirEditPanel.getText());;
            //BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
            //in.read();
            String text = jAutoDirEditPanel.getText();
            //text.g
            //BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            InputStream is = null;

            //InputStreamReader isr = new InputStreamReader(is);

            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));

            String str = "";
            String line = null;
            //Byte line = null;
            int i = 0;
            //while(in.read() != null){
            //while((line = in.read(1024)) != null){

            while((line = in.readLine()) != null){
                str = str + line + "\n";
                //System.out.println("line>>" + line);
                //resultTable.setValueAt(line, i, 0);

                i++;
            }
            statementFILEArea.setText(str);
            contentTabPanel.setSelectedIndex(0);
            in.close();
        }catch(IOException e){
            // TODO Auto-generated catch block
            //throw new LException(e);
        }
    }

    /**
     * 텍스트 파일에 텍스트 내용을 출력한다.
     */
    public void writeFile(File file, String content) throws IOException{
        BufferedOutputStream out = null;
        //BufferedWriter out = null;
        try{
            //content.set
            //out = new BufferedWriter(new FileWriter(file));
            out = new BufferedOutputStream(new FileOutputStream(file));
            //out.write(content);
            out.write(content.getBytes("UTF-8"));
            JOptionPane.showMessageDialog(fileGridTable, "파일 저장이 성공했습니다.");
        }catch(Exception e){
            System.out.println("writeFile>>" + e.toString());
        }finally{
            if(out != null)
                try{
                    out.close();
                }catch(Exception e){
                }
        }
    }

    public static void main(String args[]) throws IOException{

        new SwingTest2();

        //cm.fileRead();
    }

    //DB connectin 생성
    public void getConnection(String div){
        System.out.println("DB연결1");
        try{
            setConnectionInfo(div);

            //if(conn==null){
            //conn = null;
            //System.out.println("DB연결");

            //}
            //}catch(ClassNotFoundException e){
            // TODO Auto-generated catch block
            //throw new LException(e);
        }catch(Exception e){
            // TODO Auto-generated catch block
            //throw e;
        }
        //return conn;
    }

    //DB connectin 닫기
    public void releaseConnection(){
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException e){
            }
        }
    }

    //DB connectin 생성
    public void setConnectionInfo(String div){
        //releaseConnection();
        if(div == null || div.equals("")){
            div = "운영";
        }
        String pUrl = "";
        String pUserId = "";
        String pPwd = "";
        String pUser = "";
        System.out.print("conn_div>>>" + div);
        Properties prop = new Properties();

        try{
            //FileInputStream fis = new FileInputStream(
            //"C:/PMS/workspace/pmsweb/src/pms/pm/pma/p02/jvEbgt/cmd/db.properties");
            FileInputStream fis = new FileInputStream("C:/CodeMakerFile/db.properties");
            prop.load(fis);
        }catch(Exception e){
            // TODO Auto-generated catch block

        }
        if(div.equals("개발")){//개발
            sid = prop.getProperty("svo.dsid");
        }else if(div.equals("운영")){//운영
            sid = prop.getProperty("svo.psid");
        }else if(div.equals("교육")){//교육
            sid = prop.getProperty("svo.esid");
        }

        if(div.equals("개발")){
            /*
            url = "@165.244.179.99:1526:SVOCSTD";
            userid = "cst_app";
            pwd = "cstdpdlvlvl";
            user = "COP_MGR";//실dB계정
            */
            pUrl = "@" + prop.getProperty("svo.dserverAddress") + ":" + prop.getProperty("svo.dportNo") + ":"
                    + prop.getProperty("svo.dsid");
            pUserId = prop.getProperty("svo.duserid");
            pPwd = prop.getProperty("svo.dpassword");
            pUser = prop.getProperty("svo.user");//실dB계정

        }else if(div.equals("운영")){
            //url = "@165.244.179.100:1641:SVOCSTP";
            pUrl = "@" + prop.getProperty("svo.pserverAddress") + ":" + prop.getProperty("svo.pportNo") + ":"
                    + prop.getProperty("svo.psid");
            pUserId = prop.getProperty("svo.puserid");
            pPwd = prop.getProperty("svo.ppassword");
            pUser = "COP_MGR";//실dB계정
        }

        try{
            if(conn != null){
                conn = null;
                System.out.println("1111111");
            }else{
                System.out.println("2222222");
            }
            System.out.println(div +">>>"+pUrl + "," + pUserId + "," + pPwd);
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
            }catch(ClassNotFoundException e){
                // TODO Auto-generated catch block
                //throw new LException(e);
            }
            
            /*
            conn = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(FAILOVER=ON)(LOAD_BALANCE=ON"+
                    "(ADDRESS=(PROTOCOL=TCP)(HOST=10.253.42.153)(PORT=1521))"+
                    "(ADDRESS=(PROTOCOL=TCP)(HOST=10.253.42.154)(PORT=1521))"+
                    "(CONNECT_DATA=(SERVICE_NAME=CPXAPP)))", pUserId, pPwd);
			*/
            //DriverManager.get

            conn = DriverManager.getConnection("jdbc:oracle:thin:" + pUrl, pUserId, pPwd);//thin방식
            //conn = DriverManager.getConnection("jdbc:oracle:oci:@" + sid, pUserId, pPwd);//oci방식
            cm.setConn(conn);
        }catch(SQLException e){
            // TODO Auto-generated catch block
            //throw new LException(e);
        }

        //return conn;
        //

        //fileRead("C:/PMS/workspace/pmsweb/src/pms/pm/pma/p02/jvEbgt/cmd/db.properties");
    }

    //목록 조회
    public void connDB(){
        String connDiv = dbContComboBox.getSelectedItem().toString();
        getConnection(connDiv);

        this.setTitle("CodeMaker(" + connDiv + ")");
        searchObjList();
    }

    //목록 조회
    public void searchObjList(){
        String objDiv = objectComboBox.getSelectedItem().toString();
        String workDiv = dbWorkDivComboBox.getSelectedItem().toString();
        if(objDiv.equals("")){
            objDiv = "TABLE";
        }
        //objDiv = objectComboBox.
        String objName = searchTextField.getText();
        selObjList(objDiv, objName.trim(), workDiv);
    }

    //목록 조회
    public void searchObjContentList(){

        String objName = tfObjectContent.getText();
        selObjContentList(objName.trim());
    }

    //명명 목록 조회
    public void searchNameList(){
        String objDiv = objectComboBox.getSelectedItem().toString();
        String workDiv = dbWorkDivComboBox.getSelectedItem().toString();
        String objName = searchTextField.getText();
        String methodWork = tf1.getText();
        String file_work_div = tf2.getText();
        if(workDiv == null || workDiv.equals("")){
            workDiv = "PMA";
        }else{
            int strInx = workDiv.indexOf("|") + 1;
            int endInx = workDiv.length();
            workDiv = workDiv.substring(strInx, endInx);
        }
        String s_workDiv = tf3.getText();
        String work_k_name = tf4.getText();
        selNameList(methodWork, file_work_div, workDiv, s_workDiv, work_k_name);
    }

    //문장조회
    public void selStatement(){
        String tbName = tfTableName.getText();
        selStatement(tbName.trim());
    }

    //파일 열기
    public void openFile(){
        String openFilePath = "";
        int returnValue = fileChooser.showOpenDialog(null);
        if(returnValue == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            //openFilePath = selectedFile.getName();
            openFilePath = selectedFile.getAbsolutePath();

            fileByExt(openFilePath);

        }
    }

    //폴더 이동
    public void moveDir(String moveDiv){
        if(moveDiv.equals("MAX_TOP_DIR")){
            selDirList(upDirPath, upDir);
            selFileList(upDirPath);
        }else if(moveDiv.equals("TOP_DIR")){
            int selRow = directoryGridTable.getSelectedRow();
            if(selRow == -1){
                JOptionPane.showMessageDialog(directoryGridTable, "폴더를 선택하세요.");
            }
            //g_UpPath = directoryGridTable.getValueAt(selRow, 3).toString();
            String upPath = g_UpPath;
            upPath = directoryGridTable.getValueAt(selRow, 3).toString();
            String upDir = directoryGridTable.getValueAt(selRow, 2).toString();//g_UpDir;
            //System.out.println("upPath>>>" + upPath);
            selDirList(upPath, upDir);
            selFileList(upPath);
        }else if(moveDiv.equals("SUB_DIR")){
            int selRow = directoryGridTable.getSelectedRow();
            if(selRow == -1){
                JOptionPane.showMessageDialog(directoryGridTable, "폴더를 선택하세요.");
            }
            String dirName = directoryGridTable.getValueAt(selRow, 0).toString();

            String upDir = directoryGridTable.getValueAt(selRow, 2).toString();

            String upPath = upDir + File.separator + dirName;

            //g_UpPath = m.getValueAt(row, 3).toString();
            //g_UpDir = m.getValueAt(row, 2).toString();

            //String upPath = g_SubPath;
            //String upDir = g_UpDir;
            selDirList(upPath, upDir);
            //selFileList(upPath);
        }
    }

    //신규폴더
    public void newDir(){
        //같은 디렉토리 있는지 체크
        int selRow = directoryGridTable.getSelectedRow();
        if(selRow == -1){
            JOptionPane.showMessageDialog(directoryGridTable, "폴더를 선택하세요.");
        }else{
            String upDir = directoryGridTable.getValueAt(selRow, 2).toString();
            String upDir3 = directoryGridTable.getValueAt(selRow, 3).toString();
            String tfDirNm = tfDir.getText();
            String newDir = upDir + File.separator + tfDirNm;
            File f = new File(newDir);
            if(f.exists()){
                JOptionPane.showMessageDialog(directoryGridTable, "중복된 폴더가 존재합니다.");
            }else{
                f.mkdirs();
                selDirList(upDir, upDir3);
            }
        }
        //selFileList(upDir);
    }

    //폴더삭제
    public void delDir(){
        //서브에 내용이 있는지 체크
        int selRow = directoryGridTable.getSelectedRow();
        if(selRow == -1){
            JOptionPane.showMessageDialog(directoryGridTable, "삭제할 폴더를 선택하세요.");
        }

        String dir = directoryGridTable.getValueAt(selRow, 2).toString();
        String upDir = directoryGridTable.getValueAt(0, 2).toString();
        String upDir3 = directoryGridTable.getValueAt(0, 3).toString();

        dir = g_SubPath;
        upDir = g_UpDir;
        upDir3 = g_UpPath;

        File f = new File(dir);
        if(f.exists()){
            JOptionPane.showMessageDialog(directoryGridTable, "하위에 폴더나 파일이 존재합니다.");
        }else{
            f.delete();
            selDirList(upDir, upDir3);
        }
    }

    //신규파일
    public void newFile(){
        //같은 파일명이 있는지 체크
        String upDir = directoryGridTable.getValueAt(0, 2).toString();
        String upDir3 = directoryGridTable.getValueAt(0, 3).toString();
        String tfDirNm = tfFileDirPath.getText();
        String tfFileNm = tfFileName.getText();
        if(tfDirNm.equals("")){
            JOptionPane.showMessageDialog(directoryGridTable, "경로를 선택하세요.");
        }else{
            String newDir = tfDirNm + File.separator + tfFileNm;
            File f = new File(newDir);
            if(f.exists()){
                JOptionPane.showMessageDialog(directoryGridTable, "중복된 파일이 존재합니다.");
            }else{
                try{
                    f.createNewFile();
                }catch(IOException e){
                    // TODO Auto-generated catch block
                    //throw new LException(e);
                }
                selFileList(tfDirNm);
            }
        }
    }

    //파일삭제 부터
    public void renameFile(){
        //같은 파일명이 있는지 체크
        int selRow = fileGridTable.getSelectedRow();
        //String filePath = fileGridTable.getValueAt(selRow, 0).toString();
        if(selRow == -1){
            JOptionPane.showMessageDialog(directoryGridTable, "파일을 선택하세요.");
            return;
        }
        //fileGridTable.setF
        String fileName = fileGridTable.getValueAt(selRow, 0).toString();
        String filePath = tfFileDirPath.getText();
        String origFilePath = filePath + File.separator + fileName;

        String tfFileNm = tfFileName.getText();

        if(tfFileNm.equals("")){
            JOptionPane.showMessageDialog(fileGridTable, "변경할 파일명을 입력하세요.");
            return;
        }
        String newFilePath = filePath + File.separator + tfFileNm;
        if(filePath.equals("")){
            JOptionPane.showMessageDialog(fileGridTable, "경로를 선택하세요.");
        }else{
            String newDir = upDir;
            File file = new File(origFilePath);
            file.renameTo(new File(newFilePath));

            //System.out.println("filePath>>>"+tfDirNm);
            selFileList(filePath);
        }
    }

    //파일삭제 부터
    public void delFile(){
        //같은 파일명이 있는지 체크
        int selRow = fileGridTable.getSelectedRow();
        //String filePath = fileGridTable.getValueAt(selRow, 0).toString();

        String fileName = fileGridTable.getValueAt(selRow, 0).toString();
        String filePath = tfFileDirPath.getText();
        filePath = filePath + File.separator + fileName;

        String tfDirNm = tfFileDirPath.getText();
        String tfFileNm = tfFileName.getText();
        System.out.println("filePath>>>" + filePath);
        if(tfDirNm.equals("")){
            JOptionPane.showMessageDialog(fileGridTable, "경로를 선택하세요.");
        }else{
            String newDir = upDir;
            File f = new File(filePath);
            f.delete();
            //System.out.println("filePath>>>"+tfDirNm);
            selFileList(tfDirNm);
        }
    }

    //파일 확장자 별로 읽기
    public void fileByExt(String path){
        int extInx = path.indexOf(".");

        String ext = path.substring(path.length() - 4, path.length()).toLowerCase();

        FileSystemView view = fileChooser.getFileSystemView();

        System.out.println(view.getDefaultDirectory());

        HashMap sheetMap = new HashMap();//시트 담는 맵
        HashMap sheetColMap = new HashMap();//시트별 행
        ArrayList sheetList = new ArrayList();//시트
        //양식체크
        if(ext.equals("xlsx")){//엑셀인경우

            //JAVA API 테스트

            ApiTest at = new ApiTest();
            //sheetMap = at.readExcel2007(path);
            setExcelTable(sheetMap);
        }else{//텍스트 파일인 경우
            String ext2 = "";
            if(extInx == -1){
                ext2 = "";
            }else{
                ext2 = path.substring(path.length() - 3, path.length());
                System.out.println("ext>>>" + ext);
            }
            if(ext2.equals("xls")){
                ApiTest at = new ApiTest();
                //sheetMap = at.readExcel2007(path);
                setExcelTable(sheetMap);
            }else if(ext2.toLowerCase().equals("txt") || ext2.toLowerCase().equals("properties")){
                fileRead(path);
            }else{
                try{
                    Desktop desktop = null;
                    if(Desktop.isDesktopSupported()){
                        desktop = Desktop.getDesktop();
                    }

                    desktop.open(new File(path));
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }

            }
        }
    }

    //파일 저장
    public void saveToFile(){
        /*
        jp.add(jw);
        jp.add(button);
        int x = 500;//random.nextInt(200);
        int y = 500;//random.nextInt(200);
        popup = factory.getPopup(component, jp, x, y);
        popup.show();
        */
        try{
            int selRow = fileGridTable.getSelectedRow();

            if(selRow == -1){
                JOptionPane.showMessageDialog(fileGridTable, "저장할 파일을 선택하세요.");
            }else{
                //String filePath = fileGridTable.getValueAt(selRow, 0).toString();

                String fileName = fileGridTable.getValueAt(selRow, 0).toString();
                String filePath = tfFileDirPath.getText();
                filePath = filePath + File.separator + fileName;

                File file = new File(filePath);
                String content = "";
                content = statementFILEArea.getText();
                writeFile(file, content);
            }

        }catch(FileNotFoundException fnfe){
            System.out.println("a1>>" + fnfe.toString());

        }catch(IOException ioe){
            System.out.println("a2>>" + ioe.toString());
        }catch(Exception e){
            System.out.println("a3>>" + e.toString());
            //return;
        }
    }

    //문장실행
    public void execStatement(){
        String execStr = statementSQLArea.getText();
        String objDiv = objectComboBox.getSelectedItem().toString();
        System.out.println(execStr+"문장실행>>>>>>"+objDiv);
        if(objDiv.equals("TABLE") || objDiv.equals("VIEW")){//AM201608301711221035
        	String divStr="TR";
        	if (execStr.toLowerCase().indexOf("insert") !=-1) { // text문자열중에서 abc를 포함하는지 확인
        		divStr="TR";
        	}else{
        		if (execStr.toLowerCase().indexOf("update") !=-1) { // text문자열중에서 abc를 포함하는지 확인
        			divStr="TR";
        		}else{
        			if (execStr.toLowerCase().indexOf("delete") !=-1) { // text문자열중에서 abc를 포함하는지 확인
                		divStr="TR";

                		System.out.println("update문장실행>>>>>>TRAN");
                	}else{
                		divStr="QR";
                	}

        		}
        	}
        	        	System.out.println("update문장실행>>>>>>"+divStr);
        	if(objDiv.equals("VIEW")){
        		divStr="QR";
        	}
        	//if(execStr.toLowerCase().indexOf("insert")==-1 || execStr.toLowerCase().indexOf("update")==-1 || execStr.toLowerCase().indexOf("delete")==-1){
        	if(divStr.equals("QR")){
        	//if(execStr.toLowerCase().matches("update")){
        		HashMap result = cm.execData("TABLE", execStr);
                //result = cm.execProc("TEST");
                setResultTable(result);
        		
        	}else{
        		System.out.println("update문장실행>>>>>>"+objDiv);
        		cm.execTran("TABLE", execStr);
        	}
        }else{
        	System.out.println("update문장실행>>>>>>"+objDiv);
        	cm.execDDL(objDiv, execStr);
        	//searchObjList();
        	//ArrayList objectList=cm.makeDataObject(objDiv,"","");        	
            //setObjectTable(objectList);
            //contentTabPanel.setSelectedIndex(1);
        }
    }

    //문장부분실행
    public void execDescStatement(){
        String execStr = "";//statementArea.getText();

        String data1 = "";
        String data2 = "";

        String data11 = "";
        String dataFrom = "";
        String contentYN = "";
        String conditionYN = "";
        String table = tfTableName.getText();
        StringBuffer str1 = new StringBuffer();
        StringBuffer str2 = new StringBuffer();
        //실행 문장 만들기
        int DataRowCnt = descTable.getRowCount();
        int DataColCnt = descTable.getColumnCount();
        //Select문
        int i2 = 0;
        int i3 = 0;
        for(int i = 0; i < DataRowCnt; i++){
            data1 = descTable.getValueAt(i, 1).toString();
            data2 = descTable.getValueAt(i, 9).toString();
            //data2 = descTable.getValueAt(i, 2).toString();
            contentYN = descTable.getValueAt(i, 7).toString();
            conditionYN = descTable.getValueAt(i, 8).toString();
            //System.out.println("data2>>"+data2+","+contentYN);
            data11 = descTable.getValueAt(i, 9).toString();
            if(!contentYN.toUpperCase().equals("")){
                i2++;
                if(i2 == 1){
                    str1.append("SELECT " + data1 + "\n");
                }else{
                    str1.append("      ," + data1 + "\n");
                }
            }
            if(!conditionYN.toUpperCase().equals("")){
                i3++;
                if(i3 == 1){
                    str2.append("WHERE " + data1 + "='" + data11 + "'\n");
                }else{
                    str2.append("AND   " + data1 + "='" + data11 + "'\n");
                }
            }
        }
        dataFrom = "FROM " + table + " \n";
        execStr = str1.append(dataFrom).append(str2.toString()).toString();

        descStatementArea.setText(execStr);
        HashMap result = cm.execData("TABLE", execStr);
        setDescExecResultTable(result);

        contentTabPanel.setSelectedIndex(3);

    }

    //클립보드로 복사
    public void copyToClipboard(){
        String result = statementFILEArea.getText();
        StringSelection ss = new StringSelection(result);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
    }

    public void setCheckBox(int fieldPosition){
    //tcm[fieldPosition].
    }

    class ColorTableModel extends AbstractTableModel {

        String columnNames[] = { "Num", "컬럼", "변수", "주석", "타입", "길이", "정밀도", "본문", "조건", "조건값" };

        public int getColumnCount(){
            return columnNames.length;
        }

        public String getColumnName(int column){
            return columnNames[column];
        }

        public int getRowCount(){
            return rowData.length;
        }

        public Object getValueAt(int row, int column){
            return rowData[row][column];
        }

        public Class getColumnClass(int column){
            return (getValueAt(0, column).getClass());
        }

        public void setValueAt(Object value, int row, int column){
            rowData[row][column] = value;
        }

        public boolean isCellEditable(int row, int column){
            return (column != 0);
        }
    }

}
