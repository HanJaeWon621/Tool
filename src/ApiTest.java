import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

// import devon.core.log.LLog;

public class ApiTest {

    static int errorLine = 0;

    static Connection conn = null;

    public static void main(String[] args) throws IOException{
        /*
        String upDirPath = "E:" + File.separator + "CodeMakerFile";
        ArrayList resultList = getDirFileList(upDirPath, "DIR");
        HashMap dirMap = new HashMap();
        HashMap fileMap = new HashMap();
        //System.out.println("size>>>" + upDirPath.substring(1, (upDirPath.lastIndexOf("/")-1));
        System.out.println("size>>>" + (upDirPath.lastIndexOf(File.separator)-1));
        //upDirPath.l
        for(int i=0; i<resultList.size();i++ ){
            dirMap = (HashMap)resultList.get(i);
            //System.out.println(dirMap.get("NAME"));
        }
        */
        //�묒����쎈뒗�� db���ｋ뒗�� JV_70000_CSV_tab.txt 18222007_tab.txt JV_70000_CSV_tab3.txt
        String excelPath = "E:" + File.separator + "CodeMakerFile" + File.separator + "�쒕툕��由대━利덇�由щ���嫄댁꽕_��긽_20121227.xlsx";//JV_70000_CSV_tab
        //excelPath ="C:"+ File.separator +"Users"+ File.separator +"hjw8393"+ File.separator +"Documents"+ File.separator +"18222007_tab.txt";
        excelPath = "C:" + File.separator + "Users" + File.separator + "hjw8393" + File.separator + "Documents"
                + File.separator + "JV_70000_CSV_tab3.txt";
        //excelPath ="C:"+ File.separator +"Users"+ File.separator +"hjw8393"+ File.separator +"Documents"+ File.separator +"jv_70000_CSV.csv";
        //System.out.println("Test>>"+DateUtil.getCurrentDateString());
        String path = "C:\\PMS\\workspace\\pmsweb\\src\\kr\\co\\serveone\\FCM\\CSD\\SPMS";//�뚯뒪�뚯씪�꾩튂
        String pathFrom = "C:\\PMS\\workspace\\pmsweb\\war\\WEB-INF\\classes\\kr\\co\\serveone\\FCM\\CSD\\SPMS";//�뚯뒪�뚯씪�꾩튂
        String pathTo = "";//"C:\\�ㅻ（��\war\\WEB-INF\\classes\\kr\\co\\serveone\\FCM\\CSD\\SPMS2";//蹂듭궗�꾩튂
        //String path="C:"+"\\" +"PMS"+File.separator +"workspace"+File.separator +"pmsweb"+File.separator +"src"+File.separator +"kr"+File.separator +"co"+File.separator +"serveone"+File.separator +"FCM"+File.separator +"CSD"+File.separator +"SPMS";
        try{
            //copyDirFileList(path, pathFrom, pathTo, "NAME");//�뚯씪 蹂듭궗

            Connection co = setConnectionInfo("�꾩떆");//db�곌껐
            makeData(co);//�곗씠��異붿텧
        }catch(Exception e){
            // TODO Auto-generated catch block
            //throw new LException(e);
        }
        File myFile3 = new File(path);
        System.out.println(myFile3.listFiles());
        /*
        try{
            readCSVFile(excelPath);
            System.out.println("errorLine>>"+errorLine);
        }catch(Exception e){
            // TODO Auto-generated catch block
            System.out.println("Line Error>>"+e.toString());
        }
        */
        /*
        int startRow=1;
        int endRow=4;
        int startCol=1;
        int endCol=3;
        HashMap hm = readExcel2007(excelPath, startRow, endRow, startCol, endCol);
        //ArrayList sheetList
        ArrayList al = (ArrayList)hm.get(0);
        System.out.println("size>>>"+al.size());
        HashMap hmresult = new HashMap();
        for(int i=0; i<al.size();i++ ){
            hmresult = (HashMap)al.get(i);
            System.out.println("size>>>"+hmresult.get(0));
        }
        */
    }

    //DB connectin �앹꽦
    public static Connection setConnectionInfo(String div){
        //releaseConnection();
        if(div == null || div.equals("")){
            div = "媛쒕컻";
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
        String sid = "";
        if(div.equals("媛쒕컻")){//媛쒕컻
            sid = prop.getProperty("svo.dsid");
        }else if(div.equals("�댁쁺")){//�댁쁺
            sid = prop.getProperty("svo.psid");
        }else if(div.equals("�꾩떆")){//�꾩떆
            sid = prop.getProperty("svo.esid");
        }

        if(div.equals("媛쒕컻")){
            /*
            url = "@165.244.179.99:1526:SVOCSTD";
            userid = "cst_app";
            pwd = "cstdpdlvlvl";
            user = "COP_MGR";//�쨊B怨꾩젙
            */
            pUrl = "@" + prop.getProperty("svo.dserverAddress") + ":" + prop.getProperty("svo.dportNo") + ":"
                    + prop.getProperty("svo.dsid");
            pUserId = prop.getProperty("svo.duserid");
            pPwd = prop.getProperty("svo.dpassword");
            pUser = prop.getProperty("svo.user");//�쨊B怨꾩젙

        }else if(div.equals("�댁쁺")){
            //url = "@165.244.179.100:1641:SVOCSTP";
            pUrl = "@" + prop.getProperty("svo.pserverAddress") + ":" + prop.getProperty("svo.pportNo") + ":"
                    + prop.getProperty("svo.psid");
            pUserId = prop.getProperty("svo.puserid");
            pPwd = prop.getProperty("svo.ppassword");
            pUser = "COP_MGR";//�쨊B怨꾩젙
        }else if(div.equals("�꾩떆")){
            //url = "@165.244.179.100:1641:SVOCSTP";
            pUrl = "@" + prop.getProperty("svo.eserverAddress") + ":" + prop.getProperty("svo.eportNo") + ":"
                    + prop.getProperty("svo.esid");
            pUserId = prop.getProperty("svo.euserid");
            pPwd = prop.getProperty("svo.epassword");
            pUser = "COP_MGR";//�쨊B怨꾩젙
        }

        try{

            System.out.println(pUrl + "," + pUserId + "," + pPwd);
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
            }catch(ClassNotFoundException e){
                // TODO Auto-generated catch block
                //throw new LException(e);
            }
            conn = DriverManager.getConnection("jdbc:oracle:thin:" + pUrl, pUserId, pPwd);//thin諛⑹떇
            //conn = DriverManager.getConnection("jdbc:oracle:oci:@" + sid, pUserId, pPwd);//oci諛⑹떇
            //cm.setConn(conn);
        }catch(SQLException e){
            // TODO Auto-generated catch block
            //throw new LException(e);
        }

        return conn;
        //

        //fileRead("C:/PMS/workspace/pmsweb/src/pms/pm/pma/p02/jvEbgt/cmd/db.properties");
    }

    //泥ル떒怨��곗씠��異붿텧
    public static HashMap makeData(Connection con){
        // TODO Auto-generated method stub
        ArrayList a2 = new ArrayList();
        ArrayList resultList = new ArrayList();
        //tbName = tableName.toUpperCase();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        //Connection conn = null;titleMap
        HashMap sumMap = new HashMap();
        HashMap titleMap = new HashMap();

        ArrayList al = new ArrayList();
        String content = "";
        Byte addr = null;
        String zpcd = "";
        String zpcd_seq = "";
        String sido_nm = "";
        String gugun_nm = "";
        String doro_nm = "";
        String bd_no = "";
        String bd_no2 = "";
        String gugun_bd_nm = "";
        BufferedOutputStream out = null;
        try{
            File file=new File("C:\\upload6037462.csv");
            //OutputStream
            //BufferedWriter bw = new BufferedWriter(fw);

            StringBuffer sb = new StringBuffer();
            StringBuffer sb2 = new StringBuffer();
            sb.append(" select * ");
            sb.append(" from ( ");
            sb.append(" select rownum rn, zpcd, zpcd_seq, sido_nm, gugun_nm, doro_nm, bd_no || decode(bd_no2,'0','','*' || bd_no2) bd_no, nvl(gugun_bd_nm,' ') gugun_bd_nm, 'Y' use_yn");
            sb.append(" from new_zip_code2 ");
            sb.append(" ) ");
            //sb.append(" where rownum between 1 and  1000000 ");

            //sb.append("where rn between 1000001 and  2000000 ");
            //sb.append("where rn between 2000001 and  3000000 ");
            //sb.append("where rn between 3000001 and  4000000 ");
            //sb.append("where rn between 4000001 and  5000000 ");
            //sb.append("where rn between 5000001 and  6000000 ");
            sb.append("where rn between 6000001 and  6037462 ");



            System.out.println("aa1>"+doro_nm);
            psmt = con.prepareStatement(sb.toString());
            //psmt.setString(1, tbName);
            //psmt.setString(2, user);
            //psmt.setString(3, "1,2");
            //psmt.executeUpdate();
            rs = psmt.executeQuery();

            int m = 0;
            int r = 0;
            //try{
                //FileWriter fw=new FileWriter("C:\\upload.csv");

                //BufferedWriter out = null;

                    //content.set
                    //out = new BufferedWriter(new FileWriter(file));
                    out = new BufferedOutputStream(new FileOutputStream(file));
                    //out.write(content);

                while(rs.next()){
                    ++r;
                    ResultSetMetaData rsm = rs.getMetaData();
                    zpcd = rs.getString("zpcd");
                    zpcd_seq = rs.getString("zpcd_seq");
                    sido_nm = rs.getString("sido_nm");
                    gugun_nm = rs.getString("gugun_nm");
                    doro_nm = rs.getString("doro_nm");
                    bd_no = rs.getString("bd_no");
                    //bd_no2 = rs.getString("bd_no2"); .getBytes("EUC_KR")
                    gugun_bd_nm = rs.getString("gugun_bd_nm");
                    gugun_bd_nm = rs.getString("gugun_bd_nm");
                    //sb2.append(zpcd+ "," +zpcd_seq+ "," +sido_nm+ "," + gugun_nm+ "," + doro_nm+ "," + bd_no + "," + gugun_bd_nm +",Y").append("\n");
                    content = zpcd+ "\t" +zpcd_seq+ "\t" +sido_nm+ "\t" + gugun_nm+ "\t" + doro_nm+ "\t" + bd_no + "\t" + gugun_bd_nm+"\tY\n";
                    //aadr = ;

                    out.write(content.getBytes("EUC_KR"));
                    //bw.write(content.getBytes("EUC-KR").toString());
                    //bw.write(content);
                //bw.close();
                //fw.close();
                //out.close();
                }

            //System.out.println(sb2.toString());
            //sumMap.put("Data", resultList);
            //File file = new File("C:\\upload1000000.csv");
            /*
            try{
                //writeFile(file, sb2.toString());
            }catch(IOException e){
                // TODO Auto-generated catch block
                System.out.println("SQLException>>" + e.toString());
            }
            */
                System.out.println("last>");
        }catch(SQLException e){
            System.out.println("SQLException>>" + e.toString());
        }catch(IOException e1){
            // TODO Auto-generated catch block
            //throw new LException(e1);
            System.out.println("Exception>>" + e1.toString());
        }finally{
            if(out != null)
                try{
                    out.close();
                }catch(Exception e){
                }
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

        if(con != null){
            try{
                con.close();
            }catch(SQLException e){
            }
        }

        return sumMap;

    }

    /**
     * �띿뒪���뚯씪���띿뒪���댁슜��異쒕젰�쒕떎.
     */
    public static void writeFile(File file, String content) throws IOException{
        BufferedOutputStream out = null;
        //BufferedWriter out = null;
        try{
            //content.set
            //out = new BufferedWriter(new FileWriter(file));
            out = new BufferedOutputStream(new FileOutputStream(file));
            //out.write(content);
            out.write(content.getBytes("EUC_KR"));
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

    //CSV�뚯씪 FileChanner�댁슜 �쇰컲 Io蹂대떎 鍮좊Ⅴ��
    public static void readCSVFile(String path) throws Exception{
        long tempAmt = 0;
        long jvSumEbgtAmt = 0;
        File inFile = null;
        FileInputStream inStream = null;
        FileChannel inChannel = null;
        ByteBuffer bBuffer = null;
        String[] strBuffer = null;
        String line = "";
        String masterLine = "";
        String childLine = "";
        int totLineCnt = 0;
        String[] ar = null;
        String[] ar2 = null;
        try{
            inFile = new File(path);
            System.out.println("SIZE>>" + inFile.length() / 2);//5268641
            inStream = new FileInputStream(inFile);
            inChannel = inStream.getChannel();
            bBuffer = ByteBuffer.allocate((int)inChannel.size());
            inChannel.read(bBuffer);
            strBuffer = new String(((ByteBuffer)bBuffer.flip()).array(), "euc-kr").split("\n");
            totLineCnt = strBuffer.length;
            //totLineCnt=50777;
            System.out.println("totLineCnt>>" + totLineCnt);
            int rCnt = (totLineCnt - 1);
            for(int i = 0; i < totLineCnt; i++){
                if(i > 3){
                    line = strBuffer[i];
                    //line = strBuffer[i].replaceAll("\"", "").replaceAll("\\+", "");
                    if(!line.substring(0, 1).equals("\'")){
                        line = line.replaceAll("\"", "").replaceAll("\\+", "").replaceAll("", "");
                        //masterLine = line.replaceAll("\t", "");
                        //String[] ar2 = line.split("\t");
                        if(rCnt == i){
                            masterLine = line.replaceAll("\t", "");
                            //ar2 = line.split("\"");
                            //System.out.println(i+"蹂몃Ц>>"+ar2[3].replaceAll(",", ""));
                            //System.out.println(i+"蹂몃Ц>>"+masterLine);
                        }else{
                            masterLine = line.replaceAll("\t", "");
                            //ar2 = line.split("\t");

                            if(masterLine.indexOf(" - ") == -1){
                                System.out.println(i + "蹂몃Ц�먮윭>>" + masterLine);
                                errorLine = i;
                                break;
                            }else{
                                System.out.println(i + "蹂몃Ц>>" + masterLine);
                            }
                            //System.out.println(i+"蹂몃Ц>>"+ar2.length+ar2[1]);
                        }
                    }else{
                        //System.out.println(i + "李⑥씪��>" + line);
                        //ar = line.replaceAll("\"", "").split("\t");
                        //tempAmt =  Long.parseLong(str2);
                        //tempAmt = Long.parseLong(ar[14]);
                        //jvSumEbgtAmt = jvSumEbgtAmt + tempAmt;
                        //for(int i2 = 0; i2 < 26; i2++){
                        //System.out.print("25>>" + ar[25]);
                        //}
                    }
                }

                //if(i>8){
                //System.out.println("break:");
                //break;
                //}

            }
            //System.out.println("jvSumEbgtAmt>>" + jvSumEbgtAmt);
        }catch(IOException e){
            //
        }finally{
            try{
                if(inStream != null)
                    inStream.close();
                if(inChannel != null)
                    inChannel.close();
            }catch(IOException e){

            }

        }

    }

    //CSV�뚯씪
    public static void readCSVFile2(String path){
        try{
            long tempAmt = 0;
            long jvSumEbgtAmt = 0;
            InputStream is = null;

            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "EUC-KR"), 8000);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(new FileInputStream(path), "EUC-KR"), 8000);

            String str = "";
            String line = null;
            //Byte line = null;
            int i = 0;
            int totLineCnt = 0;
            while((line = in2.readLine()) != null){
                totLineCnt++;
            }
            System.out.println("totLineCnt>>" + totLineCnt);
            while((line = in.readLine()) != null){
                str = str + line + "\n";

                //resultTable.setValueAt(line, i, 0);

                i++;
                if(i > 4){
                    //System.out.println("a>>"+line.substring(0,1));

                    if(!line.substring(0, 1).equals("\'")){//留덉뒪��
                        //System.out.println("蹂몃Ц1>>"+str);
                        //System.out.println("蹂몃Ц1>>"+line);
                        //if(!line.substring(7,18).trim().equals("��怨�)){
                        StringTokenizer sz = new StringTokenizer(line, "\"");

                        String[] ar2 = line.split("\"");
                        if(totLineCnt == i){
                            System.out.println(i + "蹂몃Ц>>" + ar2[3].replaceAll(",", ""));
                        }else{
                            System.out.println(i + "蹂몃Ц>>" + ar2[1]);
                        }
                        /*
                        int j=1;
                        while(sz.hasMoreElements()){
                            String str1 = sz.nextElement().toString();
                            //str2 = str1;
                            if(j==4){
                                System.out.println(i+"蹂몃Ц>>"+str1);

                                ArrayList al = (ArrayList)parse(str1);
                                for(int k = 0; k < al.size(); k++){
                                    System.out.println(k +">>"+al.get(k));
                                }

                            }
                            //System.out.println(str1);
                            j++;
                        }
                        */
                        //}else{
                        //System.out.println("蹂몃Ц22>>"+str1);
                        //}

                    }else{
                        //}else if(line.substring(5, 6).equals("\'")){
                        //if(line.substring(5, 6).equals("\'")){
                        //System.out.println(i+"李⑥씪��"+line);
                        /*
                        StringTokenizer sz2= new StringTokenizer(line,"\t");
                        String[] ar = line.split("\t");
                        int m=1;
                        //sz2.nextElement();
                        for(int i2=0;i2<26;i2++){
                            try{
                                //if(ar[i2]==null){
                                  //  System.out.println(i2+"李⑥씪��>"+"");
                                //}else{
                                    System.out.println(i2+"李⑥씪��>"+ar[i2].replaceAll("\"", "").replaceAll("\'", ""));
                                //}
                            }catch(Exception e){
                                System.out.println(i2+"李⑥씪��>"+"");
                            }
                            if(i2==14){
                                tempAmt =  Long.parseLong(ar[i2]);
                                jvSumEbgtAmt = jvSumEbgtAmt + tempAmt;
                            }
                        }
                        */
                        /*
                        while(sz2.hasMoreTokens()){
                            //while(sz2.hasMoreElements()){
                            String str2 = sz2.nextElement().toString();
                            //str2 = str1;
                            //if(j==1){
                                System.out.println(m+"李⑥씪��>"+str2.replaceAll("\"", "").replaceAll("\'", ""));
                            //}
                            if(m==15){
                                tempAmt =  Long.parseLong(str2);
                                jvSumEbgtAmt = jvSumEbgtAmt + tempAmt;
                            }
                            //System.out.println(str1);
                            m++;
                        }
                        */
                        //}
                    }
                }
                /*
                if(i>16){
                    System.out.println("湲덉븸:"+jvSumEbgtAmt);
                    break;
                }
                */
            }
            in.close();
            in2.close();
        }catch(Exception e){
            // TODO Auto-generated catch block
            System.out.println("Exception>>" + e.toString());
        }
    }

    private static ArrayList parse(String str){
        String str2 = "";
        ArrayList a2 = new ArrayList();
        String[] sta = str.split(" - ");
        for(int j = 0; j < sta.length; j++){
            //System.out.println(j +">>"+sta[j]);
            if(j == 0){
                a2.add(sta[j]);
            }
            if(j == 1){
                a2.add(sta[j]);
            }
            if(j == 2){
                a2.add(sta[j].substring(0, 3));
                a2.add(sta[j].substring(4, sta[j].length()));
            }
            if(j == 3){
                a2.add(sta[j].substring(0, 3));
                a2.add(sta[j].substring(4, sta[j].length()));
            }
            if(j == 4){
                a2.add(sta[j].substring(0, 2));
                a2.add(sta[j].substring(3, sta[j].length()));
            }
            if(j == 5){
                a2.add(sta[j].substring(0, 4));
                a2.add(sta[j].substring(5, sta[j].length()));
            }
        }
        /*
        StringTokenizer sz= new StringTokenizer(str,"-");
        int i=0;
        while(sz.hasMoreElements()){
            String str1 = sz.nextElement().toString();
            str2 = str1;
            if(i==0){
                a2.add(str2.trim());
            }else if(i==1){
                a2.add(str2.trim());
            }else {
                intoArray(str1, a2);
            }
            //System.out.println(str1);
            i++;
        }
        */
        return a2;
    }

    public static ArrayList getDirFileList(String upDirPath, String fileDirDiv){
        ArrayList resultList = new ArrayList();
        ArrayList dirList = new ArrayList();
        ArrayList fileList = new ArrayList();
        HashMap dirMap = new HashMap();
        HashMap fileMap = new HashMap();

        //�뚯씪由ъ뒪�� �붾젆�좊━ 由ъ뒪��
        Calendar cc = new GregorianCalendar();
        File myFile3 = new File(upDirPath);
        for(File s : myFile3.listFiles()){
            if(s.isDirectory()){
                dirMap = new HashMap();
                dirMap.put("NAME", s.getName());
                dirMap.put("UP_NAME", s.getParentFile());
                dirMap.put("COUNT", getDirFileListCount(s.getPath()));
                dirMap.put("UP_DIR", s.getParentFile().getParentFile());
                //s.g
                dirList.add(dirMap);
                //System.out.println("aaa>>" + s.getParentFile());
            }else{
                fileMap = new HashMap();
                fileMap.put("NAME", s.getName());
                fileList.add(fileMap);
                cc.setTimeInMillis(s.lastModified());
                String cD = cc.get(Calendar.YEAR) + "" + (cc.get(Calendar.MONTH) + 1) + ""
                        + cc.get(Calendar.DAY_OF_MONTH);
                //System.out.println(">>"+cc.get(Calendar.YEAR) + (cc.get(Calendar.MONTH)+1)+ cc.get(Calendar.DAY_OF_MONTH));
                //System.out.println(cD+"><");
                //if(cD.equals("20141126")){
                if(Integer.parseInt(cD) >= 20141126){
                    //System.out.println("/pmsweb/src/kr/co/serveone/FCM/CSD/SPMS/"+s.getName());
                    System.out.println(upDirPath + "\\" + s.getName());
                }
                //System.out.println("bbbb>>" + s.getParentFile());
            }
        }
        if(fileDirDiv.equals("DIR")){
            resultList = dirList;
        }else if(fileDirDiv.equals("FILE")){
            resultList = fileList;
        }
        return resultList;
    }

    /*
     * �뚯뒪�뚯씪�꾩튂
     * �ㅼ젣蹂듭궗 �먯쿇 �꾩튂
     * 蹂듭궗 �꾩튂
     */
    public static ArrayList copyDirFileList(String upDirPath, String upDirPath2, String upDirPathTo, String fileDirDiv)
            throws Exception{
        ArrayList resultList = new ArrayList();
        ArrayList dirList = new ArrayList();
        ArrayList fileList = new ArrayList();
        HashMap dirMap = new HashMap();
        HashMap fileMap = new HashMap();
        int i = 0;
        //�뚯씪由ъ뒪�� �붾젆�좊━ 由ъ뒪��
        Calendar cc = new GregorianCalendar();
        File myFile3 = new File(upDirPath);
        for(File s : myFile3.listFiles()){
            if(s.isDirectory()){
                dirMap = new HashMap();
                dirMap.put("NAME", s.getName());
                dirMap.put("UP_NAME", s.getParentFile());
                dirMap.put("COUNT", getDirFileListCount(s.getPath()));
                dirMap.put("UP_DIR", s.getParentFile().getParentFile());
                //s.g
                dirList.add(dirMap);
                //System.out.println("aaa>>" + s.getParentFile());
            }else{
                fileMap = new HashMap();
                fileMap.put("NAME", s.getName());
                fileList.add(fileMap);
                cc.setTimeInMillis(s.lastModified());
                String cD = cc.get(Calendar.YEAR) + "" + (cc.get(Calendar.MONTH) + 1) + ""
                        + cc.get(Calendar.DAY_OF_MONTH);
                if(Integer.parseInt(cD) >= 20141126){
                    i = i + 1;
                    //System.out.println("/pmsweb/src/kr/co/serveone/FCM/CSD/SPMS/"+s.getName());
                    String copySrcFrom = upDirPath2 + "\\" + s.getName().replaceAll(".java", ".class");
                    String copySrcTo = upDirPathTo + "\\" + s.getName().replaceAll(".java", ".class");
                    System.out.println(i + ">" + copySrcFrom);
                    //System.out.println(copySrcTo);
                    try{
                        fileCopy(copySrcFrom, copySrcTo);
                    }catch(IOException e){
                        // TODO Auto-generated catch block
                        throw new Exception(e);
                    }
                }
                //System.out.println("bbbb>>" + s.getParentFile());
            }
        }
        if(fileDirDiv.equals("DIR")){
            resultList = dirList;
        }else if(fileDirDiv.equals("FILE")){
            resultList = fileList;
        }
        return resultList;
    }

    public static void fileCopy(String fromFileName, String toFileName) throws IOException{

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fcIn = null;
        FileChannel fcOut = null;

        try{

            //移댄뵾���먮낯 �뚯씪���ㅽ듃由쎌쓣 �댁뼱踰꾨┰�덈떎.
            fis = new FileInputStream(fromFileName);
            //移댄뵾��怨녹쓽 �꾩튂���뚯씪) �ㅽ듃由쇱쓣 �댁뼱踰꾨┰�덈떎.
            fos = new FileOutputStream(toFileName);

            // �낅젰�ㅽ듃由�, 異쒕젰�ㅽ듃由�媛곴컖���뚯씪 梨꾨꼸��媛��怨��듬땲��
            fcIn = fis.getChannel();
            fcOut = fos.getChannel();

            //�낅젰�ㅽ듃由쇱쑝濡�遺�꽣 媛�졇���뚯씪梨꾨꼸�먯꽌 ��硫붿냼�쒕� �몄텧�섎㈃ , 吏�� �뚯븘��踰꾪띁�닿�吏�퀬 移댄뵾�대쾭由쎈땲��
            fcIn.transferTo(0, fcIn.size(), fcOut);

        }catch(IOException e){

            throw e;

        }finally{

            if(fcOut != null)
                fcOut.close();
            if(fcIn != null)
                fcIn.close();
            if(fos != null)
                fos.close();
            if(fis != null)
                fis.close();

        }
    }

    public static String getDirFileListCount(String upDirPath){

        //�뚯씪由ъ뒪�� �붾젆�좊━ 由ъ뒪��
        int dirCnt = 0;
        int fileCnt = 0;
        File myFile3 = new File(upDirPath);
        for(File s : myFile3.listFiles()){
            if(s.isDirectory()){
                dirCnt++;
                //System.out.println("aaa>>" + s.getParentFile());
            }else{
                fileCnt++;
            }
        }
        String cnt = Integer.toString(dirCnt) + "(" + Integer.toString(fileCnt) + ")";

        return cnt;
    }

    /**
     * Input password.
     */
    public void inputPassword(){
        Console c = System.console();
        if(c == null){
            System.err.println("No console.");
            System.exit(1);
        }

        String login = c.readLine("Enter your login: ");
        char[] oldPassword = c.readPassword("Enter your old password: ");

        if(verify(login, oldPassword)){
            boolean noMatch;
            do{
                char[] newPassword1 = c.readPassword("Enter your new password: ");
                char[] newPassword2 = c.readPassword("Enter new password again: ");
                noMatch = !Arrays.equals(newPassword1, newPassword2);
                if(noMatch){
                    c.format("Passwords don't match. Try again.%n");
                }else{
                    change(login, newPassword1);
                    c.format("Password for %s changed.%n", login);
                }
                Arrays.fill(newPassword1, ' ');
                Arrays.fill(newPassword2, ' ');
            }while(noMatch);
        }

        Arrays.fill(oldPassword, ' ');

    }

    /**
     * 6.39 Desktop
     * 6.39.1
     * Using the Desktop class to launch a URL with default browser
     * �뱁솕硫��ㅽ뵂
     */
    public void openBrowser(String openUrl){
        try{
            //String openUrl="http://www.java2s.com";
            //openUrl="http://10.59.1.192";
            URI uri = new URI(openUrl);
            Desktop desktop = null;
            if(Desktop.isDesktopSupported()){
                desktop = Desktop.getDesktop();
            }

            if(desktop != null)
                desktop.browse(uri);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }catch(URISyntaxException use){
            use.printStackTrace();
        }
    }

    public void loadClass(String classNm){
        URL[] urls = null;
        System.out.println(System.getProperty("user.dir"));
        System.out.println(File.separator);

        File dir2 = new File(System.getProperty("user.dir") + File.separator + "dir" + File.separator);
        File dir = new File("C:\\PMS\\workspace\\pmsweb\\war\\WEB-INF\\classes\\pms\\pm\\pma\\p02\\jvEbgt\\cmd\\");
        try{
            URL url = dir.toURI().toURL();
            System.out.println(url);
            urls = new URL[] { url };

            ClassLoader cl = new URLClassLoader(urls);
            //ClassLoader cl = new ClassLoader(urls);
            Class cls = cl.loadClass(classNm);

            //MyClass myObj = (MyClass) cls.newInstance();
        }catch(ClassNotFoundException e){
        }catch(MalformedURLException e){
            // TODO Auto-generated catch block
            //null;
            //throw new LException(e);
        }

    }

    // Dummy verify method.
    static boolean verify(String login, char[] password){
        return true;
    }

    // Dummy change method.
    static void change(String login, char[] password){}

    /*
    public static HashMap readExcel2007(String pullPath, int startRow, int endRow, int startCol, int endCol){
        String MCFlag = "";

        HashMap sheetMap = new HashMap();//�쒗듃 �대뒗 留�
        HashMap sheetColMap = new HashMap();//�쒗듃蹂���
        ArrayList sheetList = new ArrayList();//�쒗듃

        //Workook
        File file = new File(pullPath);

        //Workbook
        XSSFWorkbook wb = null;
        try{
            wb = new XSSFWorkbook(new FileInputStream(file));
        }catch(FileNotFoundException e){
            //LLog.info.print(e.toString());
        }catch(IOException e){
            //LLog.info.print(e.toString());
        }
        return sheetMap;
    }

    private static HashMap getCell2007Val(org.apache.poi.ss.usermodel.Cell cell, int c){
        //HashMap hm =new HashMap();//蹂�꼍 org.apache.poi.ss.usermodel.Cell

        String value = "";
        HashMap hm = new HashMap();
        if(cell != null){
            switch(cell.getCellType()){ //��쓽 type����빐 泥댄겕�섍퀬 type���ㅼ젙�댁���
                case HSSFCell.CELL_TYPE_FORMULA:
                    value = cell.getCellFormula();
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    DecimalFormat df = new DecimalFormat("#.###");
                    value = "" + df.format(cell.getNumericCellValue());
                    //value = ""+df.format(Double.toString(cell.getNumericCellValue()));
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    value = "" + cell.getRichStringCellValue().getString();
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    value = "";
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    value = "" + cell.getBooleanCellValue();
                    break;
                case HSSFCell.CELL_TYPE_ERROR:
                    value = "" + cell.getErrorCellValue();
                    break;
                //default:
            }
            //System.out.println("CELL1 col="+cell.getCellNum() + " VALUE="+value); //��뿉��븳 媛믪쓣 肄섏넄��異쒕젰�댁���
            if(c == 0){
                hm.put("mergeYN", "N");
                //System.out.println("cellStyle>>" + cell.getCellStyle().toString());
                if(value.trim().length() == 0){
                    //System.out.println("cellStyle1>>" + value.trim().length());
                    hm.put("mergeYN", "Y");
                }else{
                    //System.out.println("cellStyle2>>" + value.trim().length());
                }
            }else{
                hm.put("mergeYN", "N");
            }
        }else{
            value = "";
            if(c == 0){
                if(value.trim().length() == 0){
                    hm.put("mergeYN", "Y");
                }else{
                    hm.put("mergeYN", "N");
                }
            }else{
                hm.put("mergeYN", "N");
            }

            //System.out.println("NULL CELL2");
        }
        hm.put("value", value);
        return hm;
    }

    public void urlConn(String openUrl){
        String input = "cs=utf8&query=un&x=0&y=0";

        try{

            

            URL naver = new URL("http://kin.naver.com/search/list.nhn");

            HttpURLConnection urlConn = (HttpURLConnection)naver.openConnection();

            urlConn.setDoOutput(true);

            // urlConn.setRequestMethod("POST");

            

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConn.getOutputStream()));

            bw.write(input);

            bw.flush();

            bw.close();

           

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            String inputLine;

            while((inputLine = br.readLine()) != null){

                System.out.println(inputLine);

            }

            br.close();

        }

        catch(Exception e){

            System.out.println("Exception : " + e.toString());

        }

    }

    public static void processOneSheet(String filename){
        OPCPackage pkg;
        try{
            pkg = OPCPackage.open(filename);

            XSSFReader r = new XSSFReader(pkg);
            SharedStringsTable sst = r.getSharedStringsTable();

            XMLReader parser = fetchSheetParser(sst);

            // rId2 found by processing the Workbook
            // Seems to either be rId# or rSheet#
            InputStream sheet2 = r.getSheet("rId2");
            InputSource sheetSource = new InputSource(sheet2);
            parser.parse(sheetSource);
            sheet2.close();
        }catch(Exception e){
            // TODO Auto-generated catch block
            //throw new LException(e);
        }
    }

    public static XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException{
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        ContentHandler handler = new SheetHandler(sst);
        parser.setContentHandler(handler);
        return parser;
    }

    private static class SheetHandler extends DefaultHandler {

        private SharedStringsTable sst;

        private String lastContents;

        private boolean nextIsString;

        private SheetHandler(SharedStringsTable sst) {
            this.sst = sst;
        }

        public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException{
            // c => cell
            if(name.equals("c")){
                // Print the cell reference
                System.out.print(attributes.getValue("r") + " - ");
                // Figure out if the value is an index in the SST
                String cellType = attributes.getValue("t");
                if(cellType != null && cellType.equals("s")){
                    nextIsString = true;
                }else{
                    nextIsString = false;
                }
            }
            lastContents = "";
        }

        public void endElement(String uri, String localName, String name) throws SAXException{
            if(nextIsString){
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                nextIsString = false;
            }

            // v => contents of a cell
            // Output after we've seen the string contents
            if(name.equals("v")){
                System.out.println(lastContents);
            }
        }

        public void characters(char[] ch, int start, int length) throws SAXException{
            lastContents += new String(ch, start, length);
        }
    }
    */

}
