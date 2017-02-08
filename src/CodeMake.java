
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class CodeMake {

    static String tbName = "TBPMA067";//table명

    static String user = "scott";//실dB계정

    //특정컬럼만 가져오기 위한 조건
    static String culumIdCon = ""; //"1,2";

    static String colCon = "";//"'pjt_cd','cont_cd'"; //"1,2";

    static String source = "파일에 쓰기 테스트. \n";

    static char input[] = null;

    static FileWriter fw = null;

    static BufferedWriter bw = null;

    static String path = "E:/backup/a.txt";

    static String mode = "F"; //F 파일  S: Standard Output A: File, Standard Output

    static ArrayList a2 = new ArrayList();

    static ArrayList a3 = new ArrayList();

    static String addStr1 = ""; //부가 변수1

    static String addStr2 = "i"; //부가 변수2

    private Connection conn = null;

    public CodeMake() {

    }

    public void setConn(Connection rconn){

        if(conn != null){
            System.out.println("connYes1");
            conn = null;
            conn = rconn;
        }else{
            System.out.println("connYes2");
            conn = null;
            conn = rconn;
        }
    }

    /**
     * @param args
     */
    //public static void main(String[] args){
    public static void make(){

        ArrayList al = new ArrayList();
        //makeData();
        al = a2;
        //makeLayout();
        /*
        try{
            fw = new FileWriter(path);
            bw = new BufferedWriter(fw);

            selectMake(al);
            insertMake(al);
            updateMake(al);
            deleteMake(al);

            metaParamMake(al, "prc", "save"); //프로시저 조회 파라미터 생성
            metaParamMake(al, "prc", "sel"); //프로시저 저장 파라미터 생성

            metaParamMake(al, "xml", "sel"); //queryXml 저장 파라미터 생성
            metaParamMake(al, "xml", "save");//queryXml 저장 파라미터 생성

            metaParamMake(al, "scr", "dts");//데이터셋 생성
            metaParamMake(al, "scr", "clm");//컬럼 모델 생성

            bw.close();
            if(!mode.equals("S")){
                //fileRead();
            }
        }catch(IOException e){
            // TODO Auto-generated catch block
            //System.out.println(e.toString());
        }
        */
    }

    public static ArrayList readData(String sel){
        ArrayList resultAL = new ArrayList();
        //System.out.println("readData111>>" + sel);
        try{
            //fw = new FileWriter(path);
            //bw = new BufferedWriter(fw);
            //System.out.println("readData>>" + sel);
            if(sel.equals("selectObjList")){
                //System.out.println("a3>>" + a3.size());
                resultAL = selectObjList(a3);
            }else if(sel.equals("SelectDesc")){
                resultAL = selectDesc(a2);
            }else if(sel.equals("Select")){
                resultAL = selectMake(a2);
            }else if(sel.equals("Insert")){
                resultAL = insertMake(a2);
            }else if(sel.equals("Update")){
                resultAL = updateMake(a2);
            }else if(sel.equals("Delete")){
                resultAL = deleteMake(a2);
            }else if(sel.equals("ProcSave")){
                resultAL = metaParamMake(a2, "prc", "save"); //프로시저 조회 파라미터 생성
            }else if(sel.equals("ProcTableTypeVar")){
                resultAL = metaParamMake(a2, "prc", "tableTypeVar"); //프로시저 조회 파라미터 생성
            }else if(sel.equals("ProcTableTypeVarExt")){
                resultAL = metaParamMake(a2, "prc", "tableTypeVarExt"); //프로시저 테이블타입 변수추출
            }else if(sel.equals("ProcTableTypeVarUse")){
                resultAL = metaParamMake(a2, "prc", "tableTypeVarUse"); //프로시저 테이블타입 변수사용
            }else if(sel.equals("ProcSel")){
                resultAL = metaParamMake(a2, "prc", "sel"); //프로시저 저장 파라미터 생성
            }else if(sel.equals("XmlSave")){
                resultAL = metaParamMake(a2, "xml", "save");//queryXml 저장 파라미터 생성
            }else if(sel.equals("XmlSel")){
                resultAL = metaParamMake(a2, "xml", "sel"); //queryXml 저장 파라미터 생성
            }else if(sel.equals("DataSet")){
                resultAL = metaParamMake(a2, "scr", "dts");//데이터셋 생성
            }else if(sel.equals("ColModel")){
                resultAL = metaParamMake(a2, "scr", "clm");//컬럼 모델 생성
            }else if(sel.equals("DDL")){
                resultAL = selectDDL(a2);//DDL 생성
            }else if(sel.equals("VIEW_DDL")){
                resultAL = selectViewDDL(a2);//DDL 생성
            }else{
                /*
                selectMake(a2);
                insertMake(a2);
                updateMake(a2);
                deleteMake(a2);

                metaParamMake(a2, "prc", "tableTypeVar"); //프로시저 테이블타입 변수정의 선언
                metaParamMake(a2, "prc", "tableTypeVarExt"); //프로시저 테이블타입 변수추출
                metaParamMake(a2, "prc", "tableTypeVarUse"); //프로시저 테이블타입 변수사용
                metaParamMake(a2, "prc", "save"); //프로시저 조회 파라미터 생성
                metaParamMake(a2, "prc", "sel"); //프로시저 저장 파라미터 생성

                metaParamMake(a2, "xml", "sel"); //queryXml 저장 파라미터 생성
                metaParamMake(a2, "xml", "save");//queryXml 저장 파라미터 생성

                metaParamMake(a2, "scr", "dts");//데이터셋 생성
                metaParamMake(a2, "scr", "clm");//컬럼 모델 생성
                */
            }

            //bw.close();
            if(!mode.equals("S")){
                //fileRead();
            }
        }catch(Exception e){
            // TODO Auto-generated catch block
            //System.out.println(e.toString());
        }

        return resultAL;
    }

    public static void setAddData(String str, String str2){
        addStr1 = str.trim();
        addStr2 = str2.trim();
    }

    //첫단계 데이터 추출
    public HashMap makeData(String tableName){
        // TODO Auto-generated method stub
        a2 = new ArrayList();
        ArrayList resultList = new ArrayList();
        tbName = tableName.toUpperCase();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        //Connection conn = null;titleMap
        HashMap sumMap = new HashMap();
        HashMap titleMap = new HashMap();

        ArrayList al = new ArrayList();
        String RN = "";
        String COL = "";
        String VAL_COL = "";
        String COMMENTS = "";
        String DATA_TYPE = "";
        String DATA_LENGTH = "";
        String DATA_PRECISION = "";
        String DATA_SCALE = "";
        String NULLABLE = "";
        String COLUMN_ID = "";
        String PK_YN = "";
        try{

            StringBuffer sb = new StringBuffer();
            sb.append("     SELECT   ROWNUM RN, COLUMN_ID, UPPER(A.COLUMN_NAME) COL");
            sb.append("             ,REPLACE(RPAD(UPPER(A.COLUMN_NAME),20,'='),'=',' ') COL2 ");
            sb
                    .append("             ,REPLACE(RPAD(LOWER( SUBSTR(A.COLUMN_NAME,1,1)) || SUBSTR(REPLACE(INITCAP(REPLACE(A.COLUMN_NAME,'_',' ')),' '),2) ,20,'='),'=',' ') VAL_COL ");
            sb
                    .append("             ,NVL(COMMENTS,' ') COMMENTS,DATA_TYPE,DATA_LENGTH,NVL(DATA_PRECISION,0) DATA_PRECISION ,NVL(DATA_SCALE,0) DATA_SCALE ,NULLABLE ");
            sb.append("             ,(SELECT DECODE(COUNT(*),0, 'N','Y') PK_YN ");
            sb.append("              FROM ALL_cons_columns ");
            sb.append("              WHERE CONSTRAINT_NAME='PK_' || A.TABLE_NAME ");
            sb.append("              AND   COLUMN_NAME = A.COLUMN_NAME) PK_YN ");
            //sb.append("     FROM   ALL_COL_COMMENTS A, ALL_TAB_COLUMNS B ");
            sb.append("     FROM   USER_COL_COMMENTS A, USER_TAB_COLUMNS B ");
            sb.append("     WHERE  A.TABLE_NAME=B.TABLE_NAME ");
            //sb.append("     AND    A.OWNER = B.OWNER ");
            sb.append("     AND    A.COLUMN_NAME = B.COLUMN_NAME ");
            sb.append("     AND    UPPER(A.TABLE_NAME) = UPPER(?) ");
            //sb.append("     AND    A.OWNER IN(?) ");
            //sb.append("     AND   A.OWNER IN('COP_MGR') \n");
            if(!culumIdCon.equals("")){
                sb.append("     AND    B.COLUMN_ID IN(" + culumIdCon + ") ");
            }
            if(!colCon.equals("")){
                sb.append("     AND    LOWER(A.COLUMN_NAME) IN(" + colCon + ") ");
            }
            sb.append("     ORDER  BY COLUMN_ID ");
            //sb.append(") ");
            System.out.println("작성쿼리>>"+sb.toString()); 	
            psmt = conn.prepareStatement(sb.toString());
            psmt.setString(1, tbName);
            //psmt.setString(2, user);
            //psmt.setString(3, "1,2");
            //psmt.executeUpdate();
            rs = psmt.executeQuery();

            HashMap hm = new HashMap();
            HashMap hm2 = new HashMap();
            int m = 0;
            int r = 0;
            while(rs.next()){
                hm = new HashMap();
                r++;
                ResultSetMetaData rsm = rs.getMetaData();

                RN             = rs.getString("RN");
                COL            = rs.getString("COL");
                VAL_COL        = rs.getString("VAL_COL");
                COMMENTS       = rs.getString("COMMENTS");
                DATA_TYPE      = rs.getString("DATA_TYPE");
                DATA_LENGTH    = rs.getString("DATA_LENGTH");
                //System.out.println(COL + " " + DATA_LENGTH);
                DATA_PRECISION = rs.getString("DATA_PRECISION");
                DATA_SCALE     = rs.getString("DATA_SCALE");
                NULLABLE       = rs.getString("NULLABLE");
                COLUMN_ID      = rs.getString("COLUMN_ID");
                PK_YN          = rs.getString("PK_YN");
                hm.put("RN", RN);
                hm.put("COL", COL);
                hm.put("VAL_COL", VAL_COL);
                hm.put("COMMENTS", COMMENTS);
                hm.put("DATA_TYPE", DATA_TYPE);
                hm.put("DATA_LENGTH", DATA_LENGTH);
                hm.put("DATA_PRECISION", DATA_PRECISION);
                hm.put("DATA_SCALE", DATA_SCALE);
                hm.put("NULLABLE", NULLABLE);
                hm.put("COLUMN_ID", COLUMN_ID);
                hm.put("PK_YN", PK_YN);
                a2.add(hm);
                hm2 = new HashMap();
                for(m = 1; m <= rsm.getColumnCount(); m++){
                    COL = rsm.getColumnName(m);
                    VAL_COL = rs.getString(m);
                    if(VAL_COL == null){
                        VAL_COL = "";
                    }
                    if(r == 1){
                        titleMap.put(m, COL);
                    }
                    //hm.put(COL, COL_VAL);
                    hm2.put(m, VAL_COL);
                    ////System.out.println(COL + ":" + COL_VAL);
                    //System.out.println(COL + ":" + VAL_COL);
                }
                if(r == 1){
                    sumMap.put("Title", titleMap);
                }
                resultList.add(hm2);
            }
            sumMap.put("Data", resultList);
        }catch(SQLException e){
            //System.out.println("SQLException>>" + e.toString());
        }

        if(rs != null){
            try{
                rs.close();
            }catch(SQLException e){
            }
        }

        if(psmt != null){
            try{
                psmt.close();
            }catch(SQLException e){
            }
        }
        /*
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException e){
            }
        }
        */
        return sumMap;

    }

    //첫단계 데이터 추출
    public void makeDataExt(String objectName, String objDiv){
        // TODO Auto-generated method stub
        a2 = new ArrayList();
        tbName = objectName.toUpperCase();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        //Connection conn = null;
        String COL = "";

        String COMMENTS = "";
        System.out.println(tbName + "makeDataExt>>" + objDiv);
        try{
            HashMap hm = new HashMap();
            StringBuffer sb = new StringBuffer();
            
            System.out.println("tbName>>" + tbName);
            if(objDiv.equals("VIEW")){
                sb.append("SELECT VIEW_NAME NAME, TEXT ");
                sb.append("FROM USER_VIEWS ");
                sb.append("WHERE VIEW_NAME=? ");
                psmt = conn.prepareStatement(sb.toString());
                psmt.setString(1, tbName);
                rs = psmt.executeQuery();
                rs.next();

                String text = "";
                text = rs.getString("TEXT");
                hm = new HashMap();
                hm.put("COMMENTS", text);
                a2.add(hm);
                System.out.println("text>>" + text);	
            }else{
                sb.append("SELECT NAME, TEXT ");
                sb.append("FROM USER_SOURCE ");
                sb.append("WHERE NAME=? ");

                psmt = conn.prepareStatement(sb.toString());
                psmt.setString(1, tbName);
                rs = psmt.executeQuery();

                while(rs.next()){
                    hm = new HashMap();
                    COL = rs.getString("NAME");
                    COMMENTS = rs.getString("TEXT");
                    hm.put("COL", COL);
                    hm.put("COMMENTS", COMMENTS);
                    a2.add(hm);
                }
            }
        }catch(SQLException e){
            //System.out.println("SQLException>>" + e.toString());
        }

        if(rs != null){
            try{
                rs.close();
            }catch(SQLException e){
            }
        }

        if(psmt != null){
            try{
                psmt.close();
            }catch(SQLException e){
            }
        }
        /*
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException e){
            }
        }
        */
        //return al;

    }

    //내용으로 오브젝트 검색
    public ArrayList makeDataObjectContent(String objectName){
        // TODO Auto-generated method stub
        ArrayList a3 = new ArrayList();
        tbName = objectName.toUpperCase();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        //Connection conn = null;
        String COL = "";

        String COMMENTS = "";

        try{
            HashMap hm = new HashMap();
            StringBuffer sb = new StringBuffer();
            System.out.println("tbName>>" + tbName);

            sb.append("SELECT distinct name, TYPE ");
            sb.append("FROM USER_SOURCE ");
            sb.append("WHERE UPPER(TEXT) like UPPER(?)  ");
            sb.append("order by name ");

            psmt = conn.prepareStatement(sb.toString());
            psmt.setString(1, "%" + tbName + "%");
            rs = psmt.executeQuery();
            System.out.println("makeDataObjectContent>>" + sb.toString());
            while(rs.next()){
                hm = new HashMap();
                COL = rs.getString("NAME");
                COMMENTS = rs.getString("TYPE");
                hm.put("COL", COL);
                hm.put("COMMENTS", COMMENTS);
                a3.add(hm);
            }
        }catch(SQLException e){
            //System.out.println("SQLException>>" + e.toString());
        }

        if(rs != null){
            try{
                rs.close();
            }catch(SQLException e){
            }
        }

        if(psmt != null){
            try{
                psmt.close();
            }catch(SQLException e){
            }
        }
        /*
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException e){
            }
        }
        */
        return a3;

    }

    //첫단계 데이터 추출
    public ArrayList makeDataObject(String objDiv, String objectName, String workDiv){
        // TODO Auto-generated method stub
        ArrayList objectList = new ArrayList();
        tbName = objectName.toUpperCase();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        //Connection conn = null;
        //System.out.println("objectName>2>"+objectName);
        ArrayList al = new ArrayList();
        String TABLE_NAME = "";
        String TABLE_COMMENT = "";
        String AUTHOR = "";
        String pWorkDiv = workDiv;
        if(workDiv == null || workDiv.equals("")){
            pWorkDiv = "ACB";
        }else{
            int strInx = workDiv.indexOf("|") + 1;
            int endInx = workDiv.length();
            pWorkDiv = workDiv.substring(strInx, endInx);
        }
        if(objDiv == null || objDiv.equals("")){
            objDiv = "TABLE";
        }
        String tableWorkDiv = "";
        if(pWorkDiv.equals("PMS_COM")){
            tableWorkDiv = pWorkDiv;
        }else{
            tableWorkDiv = "TB" + pWorkDiv;
        }
        tableWorkDiv="TB";//임시
        try{
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            //conn = DriverManager.getConnection("jdbc:oracle:thin:" + url, userid, pwd);
            StringBuffer sb = new StringBuffer();

            if(objDiv.equals("TABLE")){
                sb.append("SELECT TABLE_NAME AS NAME, NVL(COMMENTS,' ') AS COMMENT1,'없음' AUTHOR \n");
                sb.append("  FROM ( \n");
                sb.append(" SELECT * \n");
                sb.append(" FROM USER_TAB_COMMENTS \n");
                sb.append(" WHERE TABLE_TYPE='TABLE' \n");
                //sb.append(" AND   OWNER IN('COP_MGR') \n");
                //sb.append(" AND   TABLE_NAME LIKE ? \n");

                sb.append(" ) \n");

                sb.append(" WHERE   TABLE_NAME || NVL(COMMENTS,' ') LIKE ? \n");
                sb.append("  ORDER BY TABLE_NAME  \n");
                //sb.append(") ");
                //System.out.println(sb.toString());
                psmt = conn.prepareStatement(sb.toString());
                //psmt.setString(1, tableWorkDiv + "%");
                psmt.setString(1, "%" + objectName + "%");
            }else{

                sb.append("SELECT OBJECT_NAME AS NAME, ' ' COMMENT1, ' ' AUTHOR \n");
                sb.append("FROM USER_OBJECTS \n");
                sb.append("WHERE OBJECT_TYPE=? \n");
                //sb.append("  AND OBJECT_NAME LIKE ?  \n");
                System.out.println("objectNameSQL>>" + objectName);
                if(!objectName.trim().equals("")){
                    sb.append("  AND OBJECT_NAME LIKE ?  \n");
                }
                psmt = conn.prepareStatement(sb.toString());

                psmt.setString(1, objDiv);
                if(pWorkDiv.equals("ACB")){
                    pWorkDiv = "HRA";
                }
                if(!objectName.trim().equals("")){
                	if(objDiv.equals("PROCEDURE")){
                        //psmt.setString(2, "SP_PMA_" + objectName + "%");
                    	psmt.setString(2, "SP_%");
                        //psmt.setString(2, "SP_" + pWorkDiv + "%");
                        //psmt.setString(2, "SP_"+ pWorkDiv + objectName + "%");
                    }else if(objDiv.equals("FUNCTION")){
                        //psmt.setString(2, "FN_" + pWorkDiv + "%");
                    	psmt.setString(2, "FN_%");
                    }else if(objDiv.equals("VIEW")){
                        psmt.setString(2, "VW_" + "%");
                    }else if(objDiv.equals("TRIGGER")){
                        psmt.setString(2, "TRI_" + "%");
                    }
                }
                
                /*
                if(!objectName.trim().equals("")){
                    psmt.setString(3, "%" + objectName + "%");
                }
                */
                System.out.println("SQL>>" + sb.toString());

            }

            //psmt.setString(2, user);

            rs = psmt.executeQuery();

            HashMap hm = new HashMap();

            while(rs.next()){
                hm = new HashMap();
                TABLE_NAME = rs.getString("NAME");
                TABLE_COMMENT = rs.getString("COMMENT1");
                AUTHOR = rs.getString("AUTHOR");
                //if(!TABLE_NAME.trim().equals("")){
                hm.put("TABLE_NAME", TABLE_NAME);
                hm.put("TABLE_COMMENT", TABLE_COMMENT);
                hm.put("AUTHOR", AUTHOR);
                //System.out.println(TABLE_NAME + "," + TABLE_COMMENT);
                objectList.add(hm);
                //}
            }
            /*
            DatabaseMetaData mtdt = conn.getMetaData();

            System.out.println(mtdt.getProcedureTerm());

            ResultSet rs2 = mtdt.getProcedures(conn.getCatalog(),"%", "%");

            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            for (int i = 1; i <= numCols; i++) {
              if (i > 1)
                System.out.print(", ");
              System.out.print(rsmd.getColumnLabel(i));
            }
            System.out.println("");
            while (rs2.next()) {
              for (int i = 1; i <= numCols; i++) {
                if (i > 1)
                  System.out.print(", ");
                System.out.print(rs2.getString(i));
              }
              System.out.println("");
            }
            */
        }catch(SQLException e){
            System.out.println("SQLException>>" + e.toString());
        }

        if(rs != null){
            try{
                rs.close();
            }catch(SQLException e){
            }
        }

        if(psmt != null){
            try{
                psmt.close();
            }catch(SQLException e){
            }
        }
        /*
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException e){
            }
        }
        */
        return objectList;
    }

    //프로시저 파라미터 추출
    public ArrayList execProcParam(String objectName){
        // TODO Auto-generated method stub
        ArrayList rsl = new ArrayList();
        String prcName = objectName.toUpperCase();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        //Connection conn = null;

        ArrayList al = new ArrayList();
        String ARGUMENT_NAME = "";
        String DATA_TYPE = "";
        String RN = "";
        String IN_OUT = "";
        try{

            StringBuffer sb = new StringBuffer();

            sb.append("SELECT ROWNUM RN, ARGUMENT_NAME, DATA_TYPE, IN_OUT ");
            sb.append("FROM USER_ARGUMENTS ");
            sb.append("WHERE OBJECT_NAME=? ");

            psmt = conn.prepareStatement(sb.toString());
            psmt.setString(1, objectName);
            rs = psmt.executeQuery();

            HashMap hm = new HashMap();
            while(rs.next()){
                hm = new HashMap();
                RN = rs.getString("RN");
                ARGUMENT_NAME = rs.getString("ARGUMENT_NAME");
                DATA_TYPE = rs.getString("DATA_TYPE");
                IN_OUT = rs.getString("IN_OUT");
                hm.put("RN", RN);
                hm.put("ARGUMENT_NAME", ARGUMENT_NAME);
                hm.put("DATA_TYPE", DATA_TYPE);
                hm.put("IN_OUT", IN_OUT);
                rsl.add(hm);
            }

        }catch(SQLException e){
            //System.out.println("SQLException>>" + e.toString());
        }

        if(rs != null){
            try{
                rs.close();
            }catch(SQLException e){
            }
        }

        if(psmt != null){
            try{
                psmt.close();
            }catch(SQLException e){
            }
        }
        /*
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException e){
            }
        }
        */
        return rsl;

    }

    //첫단계 데이터 추출
    public HashMap execProc(String prcStr, ArrayList al){
        ArrayList resultList = new ArrayList();
        HashMap sumMap = new HashMap();
        HashMap titleMap = new HashMap();
        HashMap dataMap = new HashMap();
        System.out.println("프로시저 수행결과22>>");
        CallableStatement cstmt = null;
        ResultSet rs = null;
        //프로시저명 분석해서 처리한다.
        //String sql = "{CALL SP_PMA_1210_GET_EBGT_DGR(?,?)}";
        String sql = prcStr;
        String columnValue = "";
        int columnCount = 0;
        int r = 0;//r++;
        int NUM = 0;
        int CURSOR_NUM = 0;
        String PARAM = "";
        String IN_OUT = "";
        String VAL = "";

        try{
            HashMap hm = new HashMap();
            ArrayList al2 = new ArrayList();

            cstmt = conn.prepareCall(sql);
            int rowCnt = al.size();
            for(int i = 0; i < rowCnt; i++){
                //In일경우
                hm = (HashMap)al.get(i);
                NUM = Integer.parseInt((String)hm.get("NUM"));
                PARAM = (String)hm.get("PARAM");
                IN_OUT = (String)hm.get("IN_OUT");
                VAL = (String)hm.get("VAL");
                if(IN_OUT.equals("IN")){
                    cstmt.setString(NUM, VAL);
                }else{
                    CURSOR_NUM = NUM;
                    //cstmt.registerOutParameter(1, OracleTypes.VARCHAR);
                    cstmt.registerOutParameter(NUM, OracleTypes.CURSOR);
                }
            }
            cstmt.execute();

            rs = ((OracleCallableStatement)cstmt).getCursor(CURSOR_NUM);

            columnCount = rs.getMetaData().getColumnCount();

            //rs.getMetaData().GET
            while(rs.next()){
                r++;
                if(r == 1){

                    for(int i = 1; i <= columnCount; i++){
                        columnValue = rs.getMetaData().getColumnName(i);
                        titleMap.put(i, columnValue);
                    }

                    sumMap.put("Title", titleMap);
                    //resultList.add(hm2);
                }
                dataMap = new HashMap();
                for(int j = 1; j <= columnCount; j++){
                    if(rs.getString(j) == null){
                        columnValue = "NULL";
                    }else{
                        columnValue = rs.getString(j);
                    }
                    dataMap.put(j, columnValue);
                }

                resultList.add(dataMap);

            }

            sumMap.put("Data", resultList);

            rs.close();
            rs = null;
            cstmt.close();
            cstmt = null;
        }catch(Exception e){
            // TODO Auto-generated catch block
            System.out.println("프로시저 수행결과Exception>>" + e.toString());
        }

        return sumMap;
    }

    //첫단계 명명 규칙 데이터 추출
    public ArrayList makeNameData(String methodWork2, String file_work_div, String workDiv, String s_workDiv,
            String work_k_name){
        // TODO Auto-generated method stub
        ArrayList resultList = new ArrayList();
        String methodWork = methodWork2.toUpperCase();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        //Connection conn = null;titleMap
        String DIV_NM = "";
        String S_DIV_NM = "";
        String SRC_FILE_NAME = "";
        String METHOD_K_NAME = "";
        String METHOD_E_NAME = "";
        //methodWork="AA_BB22";
        //file_work_div="AA_BB22S";
        //workDiv="PMA";
        //s_workDiv="0101";
        //work_k_name="업무";
        try{

            StringBuffer sb = new StringBuffer();

            sb.append("WITH AA AS(");
            sb
                    .append(" SELECT REPLACE(RPAD(UPPER( SUBSTR('RETRIEVE_' || ? || '_LIST',1,1)) || SUBSTR(REPLACE(INITCAP(REPLACE('RETRIEVE_' || ? || '_LIST','_',' ')),' '),2) ,20,'='),'=',' ') upMd_list, ");
            sb
                    .append("        REPLACE(RPAD(LOWER( SUBSTR('RETRIEVE_' || ? || '_LIST',1,1)) || SUBSTR(REPLACE(INITCAP(REPLACE('RETRIEVE_' || ? || '_LIST','_',' ')),' '),2) ,20,'='),'=',' ') lwMd_list, ");
            sb
                    .append("        REPLACE(RPAD(UPPER( SUBSTR('SAVE_' || ? ||'' ,1,1))     || SUBSTR(REPLACE(INITCAP(REPLACE('SAVE_' || ? ||'','_',' ')),' '),2) ,20,'='),'=',' ')     upMd_save_list, ");
            sb
                    .append("        REPLACE(RPAD(LOWER( SUBSTR('SAVE_' || ? ||'',1,1))     || SUBSTR(REPLACE(INITCAP(REPLACE('SAVE_' || ? ||'','_',' ')),' '),2) ,20,'='),'=',' ')     lwMd_save_list, ");
            sb
                    .append("        REPLACE(RPAD(UPPER( SUBSTR('DELETE_' || ? ||'' ,1,1))   || SUBSTR(REPLACE(INITCAP(REPLACE('DELETE_' || ? ||'' ,'_',' ')),' '),2) ,20,'='),'=',' ')   upMd_del_list, ");
            sb
                    .append("        REPLACE(RPAD(LOWER( SUBSTR('DELETE_' || ? ||'' ,1,1))   || SUBSTR(REPLACE(INITCAP(REPLACE('DELETE_' || ? ||'' ,'_',' ')),' '),2) ,20,'='),'=',' ')   lwMd_del_list, ");
            sb
                    .append("        REPLACE(RPAD(UPPER( SUBSTR(? ,1,1))   || SUBSTR(REPLACE(INITCAP(REPLACE(?,'_',' ')),' '),2) ,20,'='),'=',' ')   biz_work_div, ");
            sb
                    .append("       REPLACE(RPAD(LOWER( SUBSTR(?,1,1))   || SUBSTR(REPLACE(INITCAP(REPLACE(?,'_',' ')),' '),2) ,20,'='),'=',' ')   file_work_div, ");
            sb.append("        ?  workDiv, ");//17
            sb.append("        ? s_workDiv, ");
            sb.append("        'GET_' || ? || '_LIST' prc_get_div,");
            sb.append("        'SAVE_' || ? ||''prc_save_div,");
            sb.append("        'DELETE_' || ? ||'' prc_del_div,");
            sb.append("        ? work_k_name ");//22
            sb.append(" FROM DUAL ");
            sb.append(") ");
            sb
                    .append("SELECT DIV_NM, S_DIV_NM, REPLACE(SRC_FILE_NAME,' ','') SRC_FILE_NAME, METHOD_K_NAME, METHOD_E_NAME ");
            sb.append("FROM (                ");
            sb
                    .append("   SELECT  '화면' DIV_NM, 'JSP' S_DIV_NM, (SELECT workDiv || s_workDiv FROM AA) || '.jsp' SRC_FILE_NAME, (SELECT work_k_name FROM AA)  || '조회' METHOD_K_NAME ");
            sb.append("           ,(SELECT lwMd_list FROM AA) METHOD_E_NAME FROM DUAL ");
            sb.append("   UNION ALL        ");
            sb
                    .append("   SELECT  'JAVA' DIV_NM, 'CMD' S_DIV_NM, (SELECT upMd_list FROM AA) || 'Cmd' || '.java' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '조회' METHOD_K_NAME ");
            sb.append("           ,(SELECT lwMd_list FROM AA) METHOD_E_NAME FROM DUAL ");
            sb.append("   UNION ALL        ");
            sb
                    .append("   SELECT  'JAVA' DIV_NM, 'BIZ' S_DIV_NM, (SELECT biz_work_div FROM AA) || 'Biz' || '.java' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '조회' METHOD_K_NAME ");
            sb.append("           ,(SELECT lwMd_list FROM AA) METHOD_E_NAME FROM DUAL ");
            sb.append("   UNION ALL        ");
            sb
                    .append("   SELECT  'XML' DIV_NM, 'NAVI' S_DIV_NM, (SELECT file_work_div FROM AA) || '.xml' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '조회' METHOD_K_NAME ");
            sb.append("           ,(SELECT lwMd_list FROM AA) METHOD_E_NAME FROM DUAL ");
            sb.append("   UNION ALL        ");
            sb
                    .append("   SELECT  'XML' DIV_NM, 'SQL' S_DIV_NM, (SELECT file_work_div FROM AA) || '.xml' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '조회' METHOD_K_NAME ");
            sb.append("           ,(SELECT lwMd_list FROM AA) METHOD_E_NAME FROM DUAL");
            sb.append("   UNION ALL ");
            sb
                    .append("   SELECT  'DB' DIV_NM, '프로시저' S_DIV_NM, (SELECT 'SP_' || workDiv || '_' || s_workDiv || '_' ||  prc_get_div FROM AA) || '.sql' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '조회' METHOD_K_NAME");
            sb
                    .append("           ,(SELECT 'SP_' || workDiv || '_' || s_workDiv || '_' ||  prc_get_div FROM AA) METHOD_E_NAME FROM DUAL ");
            sb.append("   UNION ALL");
            sb
                    .append("   SELECT  '화면' DIV_NM, 'JSP' S_DIV_NM, (SELECT workDiv || s_workDiv FROM AA) || '.jsp' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '저장' METHOD_K_NAME ");
            sb.append("           ,(SELECT lwMd_save_list FROM AA) METHOD_E_NAME FROM DUAL ");
            sb.append("   UNION ALL        ");
            sb
                    .append("   SELECT  'JAVA' DIV_NM, 'CMD' S_DIV_NM, (SELECT upMd_save_list FROM AA) || 'Cmd' || '.java' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '저장' METHOD_K_NAME ");
            sb.append("           ,(SELECT lwMd_save_list FROM AA) METHOD_E_NAME FROM DUAL ");
            sb.append("   UNION ALL        ");
            sb
                    .append("   SELECT  'JAVA' DIV_NM, 'BIZ' S_DIV_NM, (SELECT biz_work_div FROM AA) || 'Biz' || '.java' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '저장' METHOD_K_NAME ");
            sb.append("           ,(SELECT lwMd_save_list FROM AA) METHOD_E_NAME FROM DUAL ");
            sb.append("   UNION ALL        ");
            sb
                    .append("   SELECT  'XML' DIV_NM, 'NAVI' S_DIV_NM, (SELECT file_work_div FROM AA) || '.xml' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '저장' METHOD_K_NAME ");
            sb.append("           ,(SELECT lwMd_save_list FROM AA) METHOD_E_NAME FROM DUAL ");
            sb.append("   UNION ALL        ");
            sb
                    .append("   SELECT  'XML' DIV_NM, 'SQL' S_DIV_NM, (SELECT file_work_div FROM AA) || '.xml' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '저장' METHOD_K_NAME ");
            sb.append("           ,(SELECT lwMd_save_list FROM AA) METHOD_E_NAME FROM DUAL ");
            sb.append("   UNION ALL ");
            sb
                    .append("   SELECT  'DB' DIV_NM, '프로시저' S_DIV_NM, (SELECT 'SP_' || workDiv || '_' || s_workDiv || '_' ||  prc_save_div FROM AA) || '.sql' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '저장' METHOD_K_NAME ");
            sb
                    .append("           ,(SELECT 'SP_' || workDiv || '_' || s_workDiv || '_' ||  prc_save_div FROM AA) METHOD_E_NAME FROM DUAL          ");
            sb.append("   UNION ALL ");
            sb
                    .append("   SELECT  '화면' DIV_NM, 'JSP' S_DIV_NM, (SELECT workDiv || s_workDiv FROM AA) || '.jsp' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '삭제' METHOD_K_NAME ");
            sb.append("           ,(SELECT lwMd_del_list FROM AA) METHOD_E_NAME FROM DUAL ");
            sb.append("   UNION ALL        ");
            sb
                    .append("   SELECT  'JAVA' DIV_NM, 'CMD' S_DIV_NM, (SELECT upMd_save_list FROM AA) || 'Cmd' || '.java' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '삭제' METHOD_K_NAME ");
            sb.append("           ,(SELECT lwMd_del_list FROM AA) METHOD_E_NAME FROM DUAL ");
            sb.append("   UNION ALL        ");
            sb
                    .append("   SELECT  'JAVA' DIV_NM, 'BIZ' S_DIV_NM, (SELECT biz_work_div FROM AA) || 'Biz' || '.java' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '삭제' METHOD_K_NAME ");
            sb.append("           ,(SELECT lwMd_del_list FROM AA) METHOD_E_NAME FROM DUAL ");
            sb.append("   UNION ALL        ");
            sb
                    .append("   SELECT  'XML' DIV_NM, 'NAVI' S_DIV_NM, (SELECT file_work_div FROM AA) || '.xml' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '삭제' METHOD_K_NAME ");
            sb.append("           ,(SELECT lwMd_del_list FROM AA) METHOD_E_NAME FROM DUAL ");
            sb.append("   UNION ALL        ");
            sb
                    .append("   SELECT  'XML' DIV_NM, 'SQL' S_DIV_NM, (SELECT file_work_div FROM AA) || '.xml' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '삭제' METHOD_K_NAME ");
            sb.append("           ,(SELECT lwMd_del_list FROM AA) METHOD_E_NAME FROM DUAL ");
            sb.append("   UNION ALL ");
            sb
                    .append("   SELECT  'DB' DIV_NM, '프로시저' S_DIV_NM, (SELECT 'SP_' || workDiv || '_' || s_workDiv || '_' ||  prc_del_div FROM AA) || '.sql' SRC_FILE_NAME,  (SELECT work_k_name FROM AA) || '삭제' METHOD_K_NAME ");
            sb
                    .append("           ,(SELECT 'SP_' || workDiv || '_' || s_workDiv || '_' ||  prc_del_div FROM AA) METHOD_E_NAME FROM DUAL                                               ");
            sb.append("   ) ");

            psmt = conn.prepareStatement(sb.toString());

            psmt.setString(1, methodWork);
            psmt.setString(2, methodWork);
            psmt.setString(3, methodWork);
            psmt.setString(4, methodWork);
            psmt.setString(5, methodWork);
            psmt.setString(6, methodWork);
            psmt.setString(7, methodWork);
            psmt.setString(8, methodWork);
            psmt.setString(9, methodWork);
            psmt.setString(10, methodWork);
            psmt.setString(11, methodWork);
            psmt.setString(12, methodWork);
            psmt.setString(13, methodWork);
            psmt.setString(14, methodWork);
            psmt.setString(15, file_work_div);
            psmt.setString(16, file_work_div);
            psmt.setString(17, workDiv);
            psmt.setString(18, s_workDiv);
            psmt.setString(19, methodWork);
            psmt.setString(20, methodWork);
            psmt.setString(21, methodWork);
            psmt.setString(22, work_k_name);

            //psmt.setString(2, user);
            //psmt.setString(3, "1,2");
            //psmt.executeUpdate();
            rs = psmt.executeQuery();
            //System.out.println("SQL>>>>>" + sb.toString());
            HashMap hm = new HashMap();
            int m = 0;
            int r = 0;
            while(rs.next()){
                hm = new HashMap();
                r++;
                ResultSetMetaData rsm = rs.getMetaData();

                DIV_NM = rs.getString("DIV_NM");
                S_DIV_NM = rs.getString("S_DIV_NM");
                SRC_FILE_NAME = rs.getString("SRC_FILE_NAME");
                METHOD_K_NAME = rs.getString("METHOD_K_NAME");
                METHOD_E_NAME = rs.getString("METHOD_E_NAME");

                hm.put("DIV_NM", DIV_NM);
                hm.put("S_DIV_NM", S_DIV_NM);
                hm.put("SRC_FILE_NAME", SRC_FILE_NAME);
                hm.put("METHOD_K_NAME", METHOD_K_NAME);
                hm.put("METHOD_E_NAME", METHOD_E_NAME);
                resultList.add(hm);
            }
        }catch(SQLException e){
            System.out.println("SQLException>>" + e.toString());
        }

        if(rs != null){
            try{
                rs.close();
            }catch(SQLException e){
            }
        }

        if(psmt != null){
            try{
                psmt.close();
            }catch(SQLException e){
            }
        }
        /*
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException e){
            }
        }
        */
        return resultList;

    }
  //쿼리 수행(Insert,Update,Delete)
    public void execDDL(String objctType, String execStr){
        // TODO Auto-generated method stub
        ArrayList resultList = new ArrayList();
        //tbName = tableName.toUpperCase();
        Statement stmt = null;
        ResultSet rs = null;
        //Connection conn = null;

        ArrayList al = new ArrayList();
        String COL = "";
        String COL_VAL = "";
        HashMap sumMap = new HashMap();
        try{

            StringBuffer sb = new StringBuffer();

            sb.append(execStr);
            //conn.
            stmt = conn.createStatement();
            int result = stmt.executeUpdate(sb.toString());

            int m = 0;
            int r = 0;
            //conn.commit();
            System.out.println("result>>>" + result);
            
        }catch(SQLException e){
            //System.out.println("SQLException>>" + e.toString());
        }

        if(stmt != null){
            try{
            	stmt.close();
            }catch(SQLException e){
            }
        }
        /*
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException e){
            }
        }
        */
    }
    //쿼리 수행(Insert,Update,Delete)
    public void execTran(String objctType, String execStr){
        // TODO Auto-generated method stub
        ArrayList resultList = new ArrayList();
        //tbName = tableName.toUpperCase();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        //Connection conn = null;

        ArrayList al = new ArrayList();
        String COL = "";
        String COL_VAL = "";
        HashMap sumMap = new HashMap();
        try{

            StringBuffer sb = new StringBuffer();

            sb.append(execStr);

            psmt = conn.prepareStatement(sb.toString());
            //psmt.setString(1, tbName);
            //psmt.setString(2, user);
            //rs = psmt.executeQuery();
            int result = psmt.executeUpdate();

            HashMap hm = new HashMap();
            HashMap titleMap = new HashMap();

            int m = 0;
            int r = 0;
            conn.commit();
            System.out.println("resultList>>>" + resultList.size());

        }catch(SQLException e){
            //System.out.println("SQLException>>" + e.toString());
        }

        if(rs != null){
            try{
                rs.close();
            }catch(SQLException e){
            }
        }

        if(psmt != null){
            try{
                psmt.close();
            }catch(SQLException e){
            }
        }
        /*
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException e){
            }
        }
        */
    }
    //쿼리 수행
    public HashMap execData(String objctType, String execStr){
        // TODO Auto-generated method stub
        ArrayList resultList = new ArrayList();
        //tbName = tableName.toUpperCase();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        //Connection conn = null;

        ArrayList al = new ArrayList();
        String COL = "";
        String COL_VAL = "";
        HashMap sumMap = new HashMap();
        try{

            StringBuffer sb = new StringBuffer();

            sb.append(execStr);

            psmt = conn.prepareStatement(sb.toString());
            //psmt.setString(1, tbName);
            //psmt.setString(2, user);
            //rs = psmt.executeQuery();
            rs = psmt.executeQuery();

            HashMap hm = new HashMap();
            HashMap titleMap = new HashMap();

            int m = 0;
            int r = 0;
            while(rs.next()){
                r++;
                hm = new HashMap();
                ResultSetMetaData rsm = rs.getMetaData();
                ////System.out.println("rsm.getColumnCount()>>" + rsm.getColumnCount());
                for(m = 1; m <= rsm.getColumnCount(); m++){
                    COL = rsm.getColumnName(m);
                    COL_VAL = rs.getString(m);
                    if(COL_VAL == null){
                        COL_VAL = "";
                    }
                    if(r == 1){
                        titleMap.put(m, COL);
                    }
                    //hm.put(COL, COL_VAL);
                    hm.put(m, COL_VAL);
                    ////System.out.println(COL + ":" + COL_VAL);
                }
                if(r == 1){
                    sumMap.put("Title", titleMap);
                }
                resultList.add(hm);
            }
            System.out.println("resultList>>>" + resultList.size());
            sumMap.put("Data", resultList);
        }catch(SQLException e){
            //System.out.println("SQLException>>" + e.toString());
        }

        if(rs != null){
            try{
                rs.close();
            }catch(SQLException e){
            }
        }

        if(psmt != null){
            try{
                psmt.close();
            }catch(SQLException e){
            }
        }
        /*
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException e){
            }
        }
        */
        return sumMap;

    }

    public static ArrayList selectObjList(ArrayList al){
        ArrayList resultAL = new ArrayList();
        String TABLE_NAME = "";
        String TABLE_COMMENT = "";

        HashMap hm2 = new HashMap();
        int cnt = al.size();
        int preCnt = cnt - 1;

        for(int i = 0; i < cnt; i++){
            hm2 = (HashMap)a3.get(i);
            TABLE_NAME = hm2.get("TABLE_NAME").toString();
            TABLE_COMMENT = hm2.get("TABLE_COMMENT").toString();

            if(!mode.equals("F")){
                //System.out.println(TABLE_NAME + " " + TABLE_COMMENT);
            }
            if(!mode.equals("S")){
                source = (TABLE_NAME + " " + TABLE_COMMENT);
            }
            resultAL.add(source);

        }
        return resultAL;
    }

    public static ArrayList selectDesc(ArrayList al){
        ArrayList resultAL = new ArrayList();
        String COL = "";
        String VAL_COL = "";
        String COMMENTS = "";
        String DATA_TYPE = "";
        String DATA_LENGTH = "";
        String DATA_PRECISION = "";
        String DATA_SCALE = "";
        String NULLABLE = "";
        String COLUMN_ID = "";

        HashMap hm2 = new HashMap();
        int cnt = al.size();
        int preCnt = cnt - 1;

        if(!mode.equals("F")){
            //System.out.println("--Desc문 생성  ");
        }
        if(!mode.equals("S")){
            source = ("\n --Desc문 생성 \n");
            resultAL.add(source);
        }
        for(int i = 0; i < cnt; i++){
            hm2 = (HashMap)al.get(i);
            COL = hm2.get("COL").toString();
            VAL_COL = hm2.get("VAL_COL").toString();
            COMMENTS = hm2.get("COMMENTS").toString();
            DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
            DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
            DATA_SCALE = hm2.get("DATA_SCALE").toString();
            NULLABLE = hm2.get("NULLABLE").toString();
            COLUMN_ID = hm2.get("COLUMN_ID").toString();
            ////System.out.println(COL +"\t" +VAL_COL +"\t" + COMMENTS +"\t" + DATA_LENGTH+"\t" + DATA_SCALE);
            //Select문

            if(!mode.equals("F")){
                //System.out.println(COL + " --" + COMMENTS + DATA_LENGTH);
            }
            if(!mode.equals("S")){
                source = (COL + " --" + COMMENTS + DATA_LENGTH);
            }
            resultAL.add(source);
        }
        return resultAL;
    }

    //DDL문
    public static ArrayList selectDDL(ArrayList al){
        ArrayList resultAL = new ArrayList();
        String COL = "";
        String VAL_COL = "";
        String COMMENTS = "";
        String DATA_TYPE = "";
        String DATA_LENGTH = "";
        String DATA_PRECISION = "";
        String DATA_SCALE = "";
        String NULLABLE = "";
        String COLUMN_ID = "";

        HashMap hm2 = new HashMap();
        int cnt = al.size();
        int preCnt = cnt - 1;

        for(int i = 0; i < cnt; i++){
            hm2 = (HashMap)al.get(i);
            COL = hm2.get("COL").toString();
            COMMENTS = hm2.get("COMMENTS").toString();
            if(i == 0){
                COMMENTS = "CREATE OR REPLACE " + COMMENTS;
            }
            source = (COMMENTS);
            resultAL.add(source);

        }
        return resultAL;
    }

    //VIEW_DDL문
    public static ArrayList selectViewDDL(ArrayList al){
        ArrayList resultAL = new ArrayList();
        resultAL = al;
        return resultAL;
    }

    //Select문
    public static ArrayList selectMake(ArrayList al){
        ArrayList resultAL = new ArrayList();
        String COL = "";
        String VAL_COL = "";
        String COMMENTS = "";
        String DATA_TYPE = "";
        String DATA_LENGTH = "";
        String DATA_PRECISION = "";
        String DATA_SCALE = "";
        String NULLABLE = "";
        String COLUMN_ID = "";
        String PK_YN = "";
        HashMap hm2 = new HashMap();
        int cnt = al.size();
        int preCnt = cnt - 1;
        System.out.println("cnt.size()>>" + cnt);
        for(int i = 0; i < cnt; i++){
            hm2 = (HashMap)al.get(i);
            COL = hm2.get("COL").toString();
            VAL_COL = hm2.get("VAL_COL").toString();
            COMMENTS = hm2.get("COMMENTS").toString();
            DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
            DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
            DATA_SCALE = hm2.get("DATA_SCALE").toString();
            NULLABLE = hm2.get("NULLABLE").toString();
            COLUMN_ID = hm2.get("COLUMN_ID").toString();
            ////System.out.println(COL +"\t" +VAL_COL +"\t" + COMMENTS +"\t" + DATA_LENGTH+"\t" + DATA_SCALE);
            //Select문
            if(i == 0){

                source = ("SELECT  " + COL + " --" + COMMENTS);
                resultAL.add(source);
            }else{
                source = ("                 ," + COL + " --" + COMMENTS);

                resultAL.add(source);
                //resultAL.add(source);
            }
        }
        for(int i = 0; i < cnt; i++){
            hm2 = (HashMap)al.get(i);
            COL = hm2.get("COL").toString();
            VAL_COL = hm2.get("VAL_COL").toString();
            COMMENTS = hm2.get("COMMENTS").toString();
            DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
            DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
            DATA_SCALE = hm2.get("DATA_SCALE").toString();
            NULLABLE = hm2.get("NULLABLE").toString();
            COLUMN_ID = hm2.get("COLUMN_ID").toString();
            PK_YN = hm2.get("PK_YN").toString();
            //Select문
            if(i == 0){
                source = ("  FROM " + tbName);
                //resultAL.add(source);
                resultAL.add(source);

                //source = (" WHERE " + COL + " = " + VAL_COL + " --" + COMMENTS);
                source = (" WHERE " + COL + " = " + VAL_COL + " --" + COMMENTS);
                //resultAL.add(source);
                resultAL.add(source);

            }else{
                if(PK_YN.equals("Y")){
                    source = ("   AND " + COL + " = " + VAL_COL + " --" + COMMENTS);
                    //resultAL.add(source);
                    resultAL.add(source);

                }
            }
        }
        return resultAL;
    }

    //Insert문 생성
    public static ArrayList insertMake(ArrayList al){
        ArrayList resultAL = new ArrayList();
        String COL = "";
        String VAL_COL = "";
        String COMMENTS = "";
        String DATA_TYPE = "";
        String DATA_LENGTH = "";
        String DATA_PRECISION = "";
        String DATA_SCALE = "";
        String NULLABLE = "";
        String COLUMN_ID = "";

        HashMap hm2 = new HashMap();
        int cnt = al.size();
        int preCnt = cnt - 1;

        source = ("INSERT INTO " + tbName + "(");
        resultAL.add(source);
        //resultAL.add(source);

        for(int i = 0; i < cnt; i++){
            hm2 = (HashMap)al.get(i);
            COL = hm2.get("COL").toString();
            VAL_COL = hm2.get("VAL_COL").toString();
            COMMENTS = hm2.get("COMMENTS").toString();
            DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
            DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
            DATA_SCALE = hm2.get("DATA_SCALE").toString();
            NULLABLE = hm2.get("NULLABLE").toString();
            COLUMN_ID = hm2.get("COLUMN_ID").toString();
            if(i == 0){

                source = ("              " + COL);
                resultAL.add(source);
            }else{
                source = ("              ," + COL);
                resultAL.add(source);
            }
        }

        source = (")VALUES( ");
        resultAL.add(source);

        for(int i = 0; i < cnt; i++){
            hm2 = (HashMap)al.get(i);
            COL = hm2.get("COL").toString();
            VAL_COL = hm2.get("VAL_COL").toString();
            COMMENTS = hm2.get("COMMENTS").toString();
            DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
            DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
            DATA_SCALE = hm2.get("DATA_SCALE").toString();
            NULLABLE = hm2.get("NULLABLE").toString();
            COLUMN_ID = hm2.get("COLUMN_ID").toString();
            //Select문
            if(i == 0){
                source = ("           " + VAL_COL);
                resultAL.add(source);
            }else{
                if(COMMENTS.equals("최초등록일시") || COMMENTS.equals("최종수정일시")){
                    source = ("           ,SYSDATE" + "              --" + COMMENTS);
                    resultAL.add(source);
                }else{
                    source = ("           ," + VAL_COL + " --" + COMMENTS);
                    resultAL.add(source);
                }
            }

            //resultAL.add(source);
        }
        source = (" )\n");
        resultAL.add(source);
        return resultAL;
    }

    //updae문 생성
    public static ArrayList updateMake(ArrayList al){
        ArrayList resultAL = new ArrayList();
        String COL = "";
        String VAL_COL = "";
        String COMMENTS = "";
        String DATA_TYPE = "";
        String DATA_LENGTH = "";
        String DATA_PRECISION = "";
        String DATA_SCALE = "";
        String NULLABLE = "";
        String COLUMN_ID = "";

        HashMap hm2 = new HashMap();
        int cnt = al.size();
        int preCnt = cnt - 1;

        source = ("UPDATE " + tbName);
        resultAL.add(source);
        //resultAL.add(source);

        int j = 0;
        for(int i = 0; i < cnt; i++){
            hm2 = (HashMap)al.get(i);
            COL = hm2.get("COL").toString();
            VAL_COL = hm2.get("VAL_COL").toString();
            COMMENTS = hm2.get("COMMENTS").toString();
            DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
            DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
            DATA_SCALE = hm2.get("DATA_SCALE").toString();
            NULLABLE = hm2.get("NULLABLE").toString();
            COLUMN_ID = hm2.get("COLUMN_ID").toString();
            ////System.out.println(COL +"\t" +VAL_COL +"\t" + COMMENTS +"\t" + DATA_LENGTH+"\t" + DATA_SCALE);
            //Select문
            if(NULLABLE.equals("Y") && (!COL.equals("REGPSN_ID") && !COL.equals("FST_REG_DTM"))){

                if(j == 0){
                    source = ("   SET " + COL + " = " + VAL_COL + " --" + COMMENTS);
                    resultAL.add(source);
                }else{
                    if(COL.equals("FNL_UPD_DTM")){
                        source = ("      ," + COL + " = SYSDATE" + " --" + COMMENTS);
                        resultAL.add(source);
                    }else{
                        source = ("      ," + COL + " = " + VAL_COL + " --" + COMMENTS);
                        resultAL.add(source);
                    }
                }
                j++;
                //}
            }
        }
        for(int i = 0; i < cnt; i++){
            hm2 = (HashMap)al.get(i);
            COL = hm2.get("COL").toString();
            VAL_COL = hm2.get("VAL_COL").toString();
            COMMENTS = hm2.get("COMMENTS").toString();
            DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
            DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
            DATA_SCALE = hm2.get("DATA_SCALE").toString();
            NULLABLE = hm2.get("NULLABLE").toString();
            COLUMN_ID = hm2.get("COLUMN_ID").toString();
            //Select문
            if(i == 0){
                source = (" WHERE " + COL + " = " + VAL_COL + " --" + COMMENTS);
                resultAL.add(source);
            }else{
                if(NULLABLE.equals("N")){
                    source = ("   AND " + COL + " = " + VAL_COL + " --" + COMMENTS);
                    resultAL.add(source);
                }
            }
        }

        return resultAL;
    }

    //DELETE문 생성
    public static ArrayList deleteMake(ArrayList al){
        ArrayList resultAL = new ArrayList();
        String COL = "";
        String VAL_COL = "";
        String COMMENTS = "";
        String DATA_TYPE = "";
        String DATA_LENGTH = "";
        String DATA_PRECISION = "";
        String DATA_SCALE = "";
        String NULLABLE = "";
        String COLUMN_ID = "";

        HashMap hm2 = new HashMap();
        int cnt = al.size();
        int preCnt = cnt - 1;

        source = ("DELETE FROM " + tbName);
        resultAL.add(source);

        for(int i = 0; i < cnt; i++){
            hm2 = (HashMap)al.get(i);
            COL = hm2.get("COL").toString();
            VAL_COL = hm2.get("VAL_COL").toString();
            COMMENTS = hm2.get("COMMENTS").toString();
            DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
            DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
            DATA_SCALE = hm2.get("DATA_SCALE").toString();
            NULLABLE = hm2.get("NULLABLE").toString();
            COLUMN_ID = hm2.get("COLUMN_ID").toString();
            //Select문
            if(i == 0){
                source = ("  FROM " + tbName);
                resultAL.add(source);
                source = (" WHERE " + COL + " = " + VAL_COL + " --" + COMMENTS);
                resultAL.add(source);
            }else{
                if(NULLABLE.equals("N")){
                    source = ("   AND " + COL + " = " + VAL_COL + " --" + COMMENTS);
                    resultAL.add(source);
                }
            }
        }
        return resultAL;
    }

    //Insert문 프로시저 파라미터 생성
    public static ArrayList metaParamMake(ArrayList al, String tierDiv, String actDiv){
        ////System.out.println("테스트.....  ");
        ArrayList resultAL = new ArrayList();
        String COL = "";
        String VAL_COL = "";
        String COMMENTS = "";
        String DATA_TYPE = "";
        String DATA_LENGTH = "";
        String DATA_PRECISION = "";
        String DATA_SCALE = "";
        String NULLABLE = "";
        String COLUMN_ID = "";

        HashMap hm2 = new HashMap();
        int cnt = al.size();
        int preCnt = cnt - 1;

        //프로시저단
        if(tierDiv.equals("prc")){
            if(actDiv.equals("tableTypeVar")){//테이블 타입 변수선언

                source = ("\n --테이블 타입 변수선언 생성 ");
                resultAL.add(source);

                //변수 정의부
                for(int i = 0; i < cnt; i++){
                    hm2 = (HashMap)al.get(i);
                    COL = hm2.get("COL").toString();
                    VAL_COL = hm2.get("VAL_COL").toString();
                    COMMENTS = hm2.get("COMMENTS").toString();
                    DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
                    DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
                    DATA_SCALE = hm2.get("DATA_SCALE").toString();
                    NULLABLE = hm2.get("NULLABLE").toString();
                    COLUMN_ID = hm2.get("COLUMN_ID").toString();
                    //Select문
                    source = ("TYPE " + COL + "_TABLE" + addStr1 + " IS TABLE OF " + tbName.toUpperCase() + "." + COL
                            + "%TYPE INDEX BY BINARY_INTEGER; --" + COMMENTS);
                    resultAL.add(source);
                }
                //변수 선언부
                for(int i = 0; i < cnt; i++){
                    hm2 = (HashMap)al.get(i);
                    COL = hm2.get("COL").toString();
                    VAL_COL = hm2.get("VAL_COL").toString();
                    COMMENTS = hm2.get("COMMENTS").toString();
                    DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
                    DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
                    DATA_SCALE = hm2.get("DATA_SCALE").toString();
                    NULLABLE = hm2.get("NULLABLE").toString();
                    COLUMN_ID = hm2.get("COLUMN_ID").toString();
                    //Select문
                    source = (COL + "_TAB" + addStr1 + " " + COL + "_TABLE" + addStr1 + "; --" + COMMENTS);
                    resultAL.add(source);

                }
            }
            if(actDiv.equals("tableTypeVarExt")){//테이블 타입 변수추출부
                //변수 추출부

                source = ("\nFOR LIST IN() LOOP\n");
                resultAL.add(source);

                source = ("\n" + addStr2 + ":=" + addStr2 + "+1;");
                resultAL.add(source);

                for(int i = 0; i < cnt; i++){
                    hm2 = (HashMap)al.get(i);
                    COL = hm2.get("COL").toString();
                    VAL_COL = hm2.get("VAL_COL").toString();
                    COMMENTS = hm2.get("COMMENTS").toString();
                    DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
                    DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
                    DATA_SCALE = hm2.get("DATA_SCALE").toString();
                    NULLABLE = hm2.get("NULLABLE").toString();
                    COLUMN_ID = hm2.get("COLUMN_ID").toString();
                    //Select문
                    source = (COL + "_TAB" + addStr1 + "(" + addStr2 + ") := LIST." + COL + "; --" + COMMENTS);
                    resultAL.add(source);

                }

                source = ("END LOOP;");
                resultAL.add(source);
            }
            if(actDiv.equals("tableTypeVarUse")){//테이블 타입 변수사용부
                source = ("\nFOR CNT IN 1.." + addStr2 + " LOOP");
                resultAL.add(source);

                //변수 사용부

                for(int i = 0; i < cnt; i++){
                    hm2 = (HashMap)al.get(i);
                    COL = hm2.get("COL").toString();
                    VAL_COL = hm2.get("VAL_COL").toString();
                    COMMENTS = hm2.get("COMMENTS").toString();
                    DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
                    DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
                    DATA_SCALE = hm2.get("DATA_SCALE").toString();
                    NULLABLE = hm2.get("NULLABLE").toString();
                    COLUMN_ID = hm2.get("COLUMN_ID").toString();
                    //Select문
                    source = (COL + "_TAB" + addStr1 + "(CNT)\n");
                    resultAL.add(source);

                }

                source = ("\n --INSERT문 생성 ");
                resultAL.add(source);
                source = ("INSERT INTO " + tbName + "(");
                resultAL.add(source);

                for(int i = 0; i < cnt; i++){
                    hm2 = (HashMap)al.get(i);
                    COL = hm2.get("COL").toString();
                    VAL_COL = hm2.get("VAL_COL").toString();
                    COMMENTS = hm2.get("COMMENTS").toString();
                    DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
                    DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
                    DATA_SCALE = hm2.get("DATA_SCALE").toString();
                    NULLABLE = hm2.get("NULLABLE").toString();
                    COLUMN_ID = hm2.get("COLUMN_ID").toString();
                    if(i == 0){
                        source = ("           " + COL);
                        resultAL.add(source);

                    }else{
                        source = ("          ," + COL);
                        resultAL.add(source);

                    }
                }
                source = (")VALUES( ");
                resultAL.add(source);

                for(int i = 0; i < cnt; i++){
                    hm2 = (HashMap)al.get(i);
                    COL = hm2.get("COL").toString();
                    VAL_COL = hm2.get("VAL_COL").toString();
                    COMMENTS = hm2.get("COMMENTS").toString();
                    DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
                    DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
                    DATA_SCALE = hm2.get("DATA_SCALE").toString();
                    NULLABLE = hm2.get("NULLABLE").toString();
                    COLUMN_ID = hm2.get("COLUMN_ID").toString();
                    //Select문
                    if(i == 0){
                        source = ("    " + COL + "_TAB" + addStr1 + "(CNT) \n");
                        resultAL.add(source);

                    }else{
                        if(COMMENTS.equals("최초등록일시") || COMMENTS.equals("최종수정일시")){
                            source = ("   ,SYSDATE" + "              --" + COMMENTS);
                            resultAL.add(source);

                        }else{
                            source = ("   ," + COL + "_TAB" + addStr1 + "(CNT)  --" + COMMENTS);
                            resultAL.add(source);

                        }
                    }
                }

                source = (" )\n");
                resultAL.add(source);

                source = ("\n --UPDATE문 생성  \n");
                resultAL.add(source);
                source = ("UPDATE " + tbName);
                resultAL.add(source);

                int j = 0;
                for(int i = 0; i < cnt; i++){
                    hm2 = (HashMap)al.get(i);
                    COL = hm2.get("COL").toString();
                    VAL_COL = hm2.get("VAL_COL").toString();
                    COMMENTS = hm2.get("COMMENTS").toString();
                    DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
                    DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
                    DATA_SCALE = hm2.get("DATA_SCALE").toString();
                    NULLABLE = hm2.get("NULLABLE").toString();
                    COLUMN_ID = hm2.get("COLUMN_ID").toString();
                    ////System.out.println(COL +"\t" +VAL_COL +"\t" + COMMENTS +"\t" + DATA_LENGTH+"\t" + DATA_SCALE);
                    //Select문
                    if(NULLABLE.equals("Y") && (!COL.equals("REGPSN_ID") && !COL.equals("FST_REG_DTM"))){

                        if(j == 0){
                            source = ("   SET " + COL + " = " + COL + "_TAB" + addStr1 + "(CNT) --" + COMMENTS);
                            resultAL.add(source);

                        }else{
                            if(COL.equals("FNL_UPD_DTM")){
                                source = ("      ," + COL + " = SYSDATE" + " --" + COMMENTS);
                                resultAL.add(source);

                            }else{
                                source = ("      ," + COL + " = " + COL + "_TAB" + addStr1 + "(CNT) --" + COMMENTS);
                                resultAL.add(source);

                            }
                        }
                        j++;
                        //}
                    }
                }
                for(int i = 0; i < cnt; i++){
                    hm2 = (HashMap)al.get(i);
                    COL = hm2.get("COL").toString();
                    VAL_COL = hm2.get("VAL_COL").toString();
                    COMMENTS = hm2.get("COMMENTS").toString();
                    DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
                    DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
                    DATA_SCALE = hm2.get("DATA_SCALE").toString();
                    NULLABLE = hm2.get("NULLABLE").toString();
                    COLUMN_ID = hm2.get("COLUMN_ID").toString();
                    //Select문
                    if(i == 0){
                        source = (" WHERE " + COL + " = " + COL + "_TAB" + addStr1 + "(CNT) --" + COMMENTS);
                        resultAL.add(source);

                    }else{
                        if(NULLABLE.equals("N")){
                            source = ("   AND " + COL + " = " + COL + "_TAB" + addStr1 + "(CNT) --" + COMMENTS);
                            resultAL.add(source);

                        }
                    }
                }

                source = ("END LOOP;");
                resultAL.add(source);
            }

            if(actDiv.equals("save")){
                source = (" --프로시저 Save 파라미터 문장 생성 ");
                resultAL.add(source);

                for(int i = 0; i < cnt; i++){
                    hm2 = (HashMap)al.get(i);
                    COL = hm2.get("COL").toString();
                    VAL_COL = hm2.get("VAL_COL").toString();
                    COMMENTS = hm2.get("COMMENTS").toString();
                    DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
                    DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
                    DATA_SCALE = hm2.get("DATA_SCALE").toString();
                    NULLABLE = hm2.get("NULLABLE").toString();
                    COLUMN_ID = hm2.get("COLUMN_ID").toString();
                    //Select문
                    if(i == 0){
                        source = (" p_duistate           IN VARCHAR2 --CUD구분 ");
                        resultAL.add(source);

                        source = ("," + VAL_COL + " IN VARCHAR2" + " --" + COMMENTS);
                        resultAL.add(source);

                    }else{
                        source = ("," + VAL_COL + " IN VARCHAR2" + " --" + COMMENTS);
                        resultAL.add(source);

                    }

                }
                source = (",returnVal            OUT NUMBER -- 처리결과 ");

                resultAL.add(source);
            }

            if(actDiv.equals("sel")){
                source = (" --프로시저 Select 파라미터 문장 생성 ");
                resultAL.add(source);

                for(int i = 0; i < cnt; i++){
                    hm2 = (HashMap)al.get(i);
                    COL = hm2.get("COL").toString();
                    VAL_COL = hm2.get("VAL_COL").toString();
                    DATA_TYPE = hm2.get("DATA_TYPE").toString();
                    COMMENTS = hm2.get("COMMENTS").toString();
                    DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
                    DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
                    DATA_SCALE = hm2.get("DATA_SCALE").toString();
                    NULLABLE = hm2.get("NULLABLE").toString();
                    COLUMN_ID = hm2.get("COLUMN_ID").toString();
                    //Select문
                    if(i == 0){
                        source = (" " + VAL_COL + " IN VARCHAR2" + " --" + COMMENTS);
                        resultAL.add(source);

                    }else{
                        if(!VAL_COL.equals("regpsnId") && !VAL_COL.equals("fstRegDtm") && !VAL_COL.equals("updpsnId")
                                && !VAL_COL.equals("fnlUpdDtm")){
                            source = ("," + VAL_COL + " IN VARCHAR2" + " --" + COMMENTS);
                            resultAL.add(source);

                        }
                    }
                }
                source = (",resultSet            OUT sys_refcursor ");
                resultAL.add(source);

            }
        }

        //XML단
        if(tierDiv.equals("xml")){
            if(actDiv.equals("save")){
                source = ("--Xml Save 파라미터 문장 생성 ");
                resultAL.add(source);

                for(int i = 0; i < cnt; i++){
                    hm2 = (HashMap)al.get(i);
                    COL = hm2.get("COL").toString();
                    VAL_COL = hm2.get("VAL_COL").toString().trim();
                    COMMENTS = hm2.get("COMMENTS").toString();
                    DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
                    DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
                    DATA_SCALE = hm2.get("DATA_SCALE").toString();
                    NULLABLE = hm2.get("NULLABLE").toString();
                    COLUMN_ID = hm2.get("COLUMN_ID").toString();
                    //Select문
                    if(i == 0){
                        source = (" ${duistate:inparam} ");
                        resultAL.add(source);

                        source = (",${" + VAL_COL + ":inparam} ");
                        resultAL.add(source);

                    }else{

                        source = (",${" + VAL_COL + ":inparam} ");
                        resultAL.add(source);

                    }
                }

                source = (",${param:outparam} ");
                resultAL.add(source);

            }

            if(actDiv.equals("sel")){

                source = ("--Xml Select 파라미터 문장 생성 ");
                resultAL.add(source);

                for(int i = 0; i < cnt; i++){
                    hm2 = (HashMap)al.get(i);
                    COL = hm2.get("COL").toString();
                    VAL_COL = hm2.get("VAL_COL").toString().trim();
                    DATA_TYPE = hm2.get("DATA_TYPE").toString();
                    COMMENTS = hm2.get("COMMENTS").toString();
                    DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
                    DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
                    DATA_SCALE = hm2.get("DATA_SCALE").toString();
                    NULLABLE = hm2.get("NULLABLE").toString();
                    COLUMN_ID = hm2.get("COLUMN_ID").toString();
                    //Select문
                    if(i == 0){
                        source = (" ${" + VAL_COL + ":inparam}" + " ");
                        resultAL.add(source);

                    }else{
                        if(!VAL_COL.equals("regpsnId") && !VAL_COL.equals("fstRegDtm") && !VAL_COL.equals("updpsnId")
                                && !VAL_COL.equals("fnlUpdDtm")){
                            source = (",${" + VAL_COL + ":inparam}" + " ");
                            resultAL.add(source);

                        }
                    }
                }
                source = (",${resultSet:oraclecursor:outparam} ");
                resultAL.add(source);

            }
        }

        //화면단
        if(tierDiv.equals("scr")){
            String align = "left";
            String render = ", renderer: 'number'";
            String type = ",type: 'number'";
            if(actDiv.equals("dts")){
                source = ("--데이터셋 생성 ");
                resultAL.add(source);

                for(int i = 0; i < cnt; i++){
                    hm2 = (HashMap)al.get(i);
                    COL = hm2.get("COL").toString();
                    VAL_COL = hm2.get("VAL_COL").toString().trim();
                    COMMENTS = hm2.get("COMMENTS").toString();
                    DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
                    DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
                    DATA_SCALE = hm2.get("DATA_SCALE").toString();
                    NULLABLE = hm2.get("NULLABLE").toString();
                    COLUMN_ID = hm2.get("COLUMN_ID").toString();
                    //Select문
                    //Select문
                    if(DATA_TYPE.equals("NUMBER")){
                        align = "right";
                        type = ",type: 'number'";
                    }else{
                        align = "left";
                        type = "";
                    }
                    if(!VAL_COL.equals("regpsnId") && !VAL_COL.equals("fstRegDtm") && !VAL_COL.equals("updpsnId")
                            && !VAL_COL.equals("fnlUpdDtm")){
                        if(i == 0){
                            source = (" { id: '" + VAL_COL + "'" + type + " }" + "//" + COMMENTS);
                            resultAL.add(source);

                        }else{
                            source = (",{ id: '" + VAL_COL + "'" + type + " }" + "//" + COMMENTS);
                            resultAL.add(source);

                        }
                    }
                }
            }

            if(actDiv.equals("clm")){
                source = ("--컬럼모델 생성 ");
                resultAL.add(source);

                for(int i = 0; i < cnt; i++){
                    hm2 = (HashMap)al.get(i);
                    COL = hm2.get("COL").toString();
                    VAL_COL = hm2.get("VAL_COL").toString().trim();
                    DATA_TYPE = hm2.get("DATA_TYPE").toString();
                    COMMENTS = hm2.get("COMMENTS").toString();
                    DATA_LENGTH = hm2.get("DATA_LENGTH").toString();
                    DATA_PRECISION = hm2.get("DATA_PRECISION").toString();
                    DATA_SCALE = hm2.get("DATA_SCALE").toString();
                    NULLABLE = hm2.get("NULLABLE").toString();
                    COLUMN_ID = hm2.get("COLUMN_ID").toString();
                    //Select문
                    if(DATA_TYPE.equals("NUMBER")){
                        align = "right";
                        render = ", renderer: 'number'";
                    }else{
                        align = "left";
                        render = "";
                    }
                    if(!VAL_COL.equals("regpsnId") && !VAL_COL.equals("fstRegDtm") && !VAL_COL.equals("updpsnId")
                            && !VAL_COL.equals("fnlUpdDtm")){

                        if(i == 0){

                            source = (" {field: '" + VAL_COL + "', label: '" + COMMENTS + "', width:" + DATA_LENGTH
                                    + ", sortable: true, align: '" + align + "'" + render + "}");
                            resultAL.add(source);

                        }else{
                            source = (",{field: '" + VAL_COL + "', label: '" + COMMENTS + "', width:" + DATA_LENGTH
                                    + ", sortable: true, align: '" + align + "'" + render + "}");
                            resultAL.add(source);

                        }
                    }

                }
            }
        }
        return resultAL;
    }

    //파일쓰기
    public static void fileWrite(String src){
        input = new char[src.length()];
        source.getChars(0, src.length(), input, 0);
        try{
            bw.write(input);
        }catch(IOException e){
            // TODO Auto-generated catch block
            //throw new LException(e);
        }
    }

    //파일읽기
    public static void fileRead(){
        //System.out.println("파일읽기 시작>>>");
        try{
            BufferedReader in = new BufferedReader(new FileReader(path));
            String str = "";
            String line = null;

            while((line = in.readLine()) != null){
                //str += line;
                //System.out.println(line);
            }
            in.close();
        }catch(IOException e){
            // TODO Auto-generated catch block
            //throw new LException(e);
        }

    }

    //데이터베이스 LONG타입을 스트링으로 받기
    public static String getLargerString(ResultSet rs, int columnIndex) throws SQLException{

        InputStream in = null;
        int BUFFER_SIZE = 1024;
        try{
            rs.next();
            in = rs.getAsciiStream(columnIndex);
            if(in == null){
                return "";
            }

            byte[] arr = new byte[BUFFER_SIZE];
            StringBuffer buffer = new StringBuffer();
            int numRead = in.read(arr);
            while(numRead != -1){
                buffer.append(new String(arr, 0, numRead));
                numRead = in.read(arr);
            }
            return buffer.toString();
        }catch(Exception e){
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }
}
