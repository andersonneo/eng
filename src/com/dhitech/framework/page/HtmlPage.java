package com.dhitech.framework.page;

/**
 * <p>Title: 페이징</p>
 * <p>Description: 페이징 컴포넌트</p>
 * <p>Copyright: Copyright (c) 안랩유비웨어 2004</p>
 * <p>Company: www.ubi-ware.com</p>
 * @author yunkidon yunkidon@ubi-ware.com
 * @version 1.0
 */

public class HtmlPage {
    private int totalCnt = 0; //총 게시물수
    private int currentPage = 1; //현재페이지번호
    private int rowPerPage = 10; //페이지당 게시물건수
    private int pageCnt = 10; //화면에 보여질 페이지 수
    private int totalPage = 0; //총 페이지 수
    private String queryString = ""; //페이징 할때 따라가야할 조건
    private String pageParam = ""; //페이지 번호로 쓸 파라메타명
    private String formName = ""; //printPage4에서 쓰일 폼 이름
    private String servletName = ""; //서블릿명

    /**
     * default constructor
     */
    public HtmlPage() {}

    public String getFormName() {
        return formName;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getPageParam() {
        return pageParam;
    }

    public int getTotalCnt() {
        return totalCnt;
    }

    public int getRowPerPage() {
        return rowPerPage;
    }

    public int getPageCnt() {
        return pageCnt;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public String setServletName() {
        return servletName;
    }

    public void setTotalCnt(int t) {
        this.totalCnt = t;
    }

    public void setRowPerPage(int r) {
        this.rowPerPage = r;
    }

    public void setPageCnt(int p) {
        this.pageCnt = p;
    }

    public void setCurrentPage(int c) {
        this.currentPage = c;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setPageParam(String pageParam) {
        this.pageParam = pageParam;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    /**
     * <b>사용법</b>
     * <pre>
     int cnt = 0;

     String p=request.getParameter("p");
     int pp = (p == null) ? 1 : Integer.parseInt(p);
     int pageRow = 15;
     int startRow = pageRow*(pp-1);

     for{

     -----------------------------------
     }

     <jsp:useBean id='HtmlPage' scope='page' class='com.moneta.pda.util.HtmlPage' />
     <jsp:setProperty name='HtmlPage' property='totalCnt' value='<%=cnt%>'   / >
     <jsp:setProperty name='HtmlPage' property='currentPage' value='<%=pp%>'     / >
     <jsp:setProperty name='HtmlPage' property='rowPerPage' value='<%=pageRow%>'  />
     <%=HtmlPage.printPage()%>
     <script>
      function goPage(p) {
      location.href =  p;
     }
     </script>
     </pre>
     * @return String 페이징 전체열을 리턴함
     */
    public String printPage() {
        StringBuffer sb = new StringBuffer();

        int nextPage = 0;
        int prevPage = 0;

        int startPage = 0;
        int endPage = 0;

        int startPage2 = 0;
        int endPage2 = 0;

        int firstPage = 1;
        int lastPage = 1;

        totalPage = (int) (totalCnt / rowPerPage);
        totalPage = (totalPage * rowPerPage != totalCnt) ? totalPage + 1 :
                    totalPage;
        totalPage = (totalPage == 0) ? 1 : totalPage;

        prevPage = (currentPage < 2) ? 1 : currentPage - 1;
        nextPage = (currentPage >= totalPage) ? totalPage : currentPage + 1;

        startPage = (int) ((currentPage - 1) / pageCnt) * pageCnt + 1;

        endPage = (int) ((((startPage - 1) + pageCnt) / pageCnt) * pageCnt);

        endPage = (totalPage <= endPage) ? totalPage : endPage;

        startPage2 = ((startPage - 1) < 1) ? 1 : startPage - 1;
        endPage2 = ((endPage + 1) > totalPage) ? totalPage : endPage + 1;

        lastPage = totalPage;

        if (firstPage <= startPage2 && currentPage != 1) {
            sb.append("<a href=\"#\" onclick=\"goPage(" + firstPage +
                      ");\"><style=\"font-size:9pt; font-family:돋움\">"
                      + "◀◀"
                      + "</font></a>&nbsp;\n");

        }
        if (startPage != startPage2) {
            sb.append("&nbsp;<a href=\"#\" onclick=\"goPage(" + startPage2 +
                      ");\"><style=\"font-size:9pt; font-family:돋움\">"
                      + "◀"
                      + "</font></a>&nbsp;\n");

        }
        for (int i = startPage; i <= endPage; i++) {
            if (i == currentPage) {
                sb.append(" &nbsp;" + i + " &nbsp;");
            } else {
                sb.append("<a href=\"#\" onclick=\"goPage(" + i + ");\">[" + i + "]</a> \n");
            }
        }
        if (endPage != endPage2) {
            sb.append("&nbsp; <a href=\"#\" onclick=\"goPage(" + endPage2 +
                      ");\"><style=\"font-size:9pt; font-family:돋움\">"
                      + "▶"
                      + "</font></a>&nbsp;\n");
        }
        if (lastPage >= endPage2 && currentPage != lastPage) {
            sb.append("&nbsp; <a href=\"#\" onclick=\"goPage(" + lastPage +
                      ");\"><style=\"font-size:9pt; font-family:돋움\">"
                      + "▶▶"
                      + "</font></a>&nbsp;\n");
        }
        return sb.toString();
    }

    /**
     * 처음과 끝으로 돌아가는 버튼 기능 없슴.
     * @return String 페이징 스크립트
     * */
    public String printPage2() {
        StringBuffer sb = new StringBuffer();

        int nextPage = 0;
        int prevPage = 0;

        int startPage = 0;
        int endPage = 0;

        int startPage2 = 0;
        int endPage2 = 0;

        totalPage = (int) (totalCnt / rowPerPage);
        totalPage = (totalPage * rowPerPage != totalCnt) ? totalPage + 1 :
                    totalPage;
        totalPage = (totalPage == 0) ? 1 : totalPage;

        prevPage = (currentPage < 2) ? 1 : currentPage - 1;
        nextPage = (currentPage >= totalPage) ? totalPage : currentPage + 1;

        startPage = (int) ((currentPage - 1) / pageCnt) * pageCnt + 1;

        endPage = (int) ((((startPage - 1) + pageCnt) / pageCnt) * pageCnt);

        endPage = (totalPage <= endPage) ? totalPage : endPage;

        startPage2 = ((startPage - 1) < 1) ? 1 : startPage - 1;
        endPage2 = ((endPage + 1) > totalPage) ? totalPage : endPage + 1;

        pageParam = "".equals(pageParam) ? "currentPage" : pageParam;

        if (startPage != startPage2) {
            sb.append("&nbsp; <a href=\"?" + pageParam + "=" + startPage2 +
                      "\"><img src=\"../image/01_course/bt_s_pre.gif\" width=\"15\" height=\"14\" hspace=\"10\" align=\"absmiddle\"></a>&nbsp;\n");

        }
        for (int i = startPage; i <= endPage; i++) {
            if (i == currentPage) {
                sb.append(" &nbsp;" + i + " &nbsp;");
            } else {
                sb.append("<a href=\"?" + pageParam + "=" + i + "\">" + i + "</a> \n");
            }
        }
        if (endPage != endPage2) {
            sb.append("&nbsp; <a href=\"?" + pageParam + "=" + endPage2 +
                      "\"><img src=\"../image/01_course/bt_s_next.gif\" width=\"15\" height=\"14\" hspace=\"10\" align=\"absmiddle\"></a>&nbsp;\n");

        }
        return sb.toString();
    }

    public String printPage3() {
        StringBuffer sb = new StringBuffer();

        int nextPage = 0;
        int prevPage = 0;

        int startPage = 0;
        int endPage = 0;

        int startPage2 = 0;
        int endPage2 = 0;

        int firstPage = 1;
        int lastPage = 1;

        totalPage = (int) (totalCnt / rowPerPage);
        totalPage = (totalPage * rowPerPage != totalCnt) ? totalPage + 1 :
                    totalPage;
        totalPage = (totalPage == 0) ? 1 : totalPage;

        prevPage = (currentPage < 2) ? 1 : currentPage - 1;
        nextPage = (currentPage >= totalPage) ? totalPage : currentPage + 1;

        startPage = (int) ((currentPage - 1) / pageCnt) * pageCnt + 1;

        endPage = (int) ((((startPage - 1) + pageCnt) / pageCnt) * pageCnt);

        endPage = (totalPage <= endPage) ? totalPage : endPage;

        startPage2 = ((startPage - 1) < 1) ? 1 : startPage - 1;
        endPage2 = ((endPage + 1) > totalPage) ? totalPage : endPage + 1;

        lastPage = totalPage;

        if (startPage != startPage2) {
            sb.append("&nbsp; <a href=\"#\" onclick=\"goPage(" + startPage2 +
                      ");\"><style=\"font-size:9pt; font-family:돋움\"><img src=\"../image/01_course/bt_s_pre.gif\" width=\"15\" height=\"14\" hspace=\"10\" align=\"absmiddle\" border=\"0\"></font></a>&nbsp;\n");
        }

        for (int i = startPage; i <= endPage; i++) {
            if (i == currentPage) {
                sb.append("&nbsp;<strong>" + i + "</strong>&nbsp;");
            } else {
                sb.append("&nbsp;<a href=\"#\" onclick=\"goPage(" + i + ");\">[" + i + "]</a> \n");
            }
        }
        if (endPage != endPage2) {
            sb.append("&nbsp; <a href=\"#\" onclick=\"goPage(" + endPage2 +
                      ");\"><style=\"font-size:9pt; font-family:돋움\"><img src=\"../image/01_course/bt_s_next.gif\" width=\"15\" height=\"14\" hspace=\"10\" align=\"absmiddle\" border=\"0\"></font></a>&nbsp;\n");
        }
        return sb.toString();
    }

}
