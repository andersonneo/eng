<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="com.eng.framework.tray.Tray"%>
<%@page import="com.eng.framework.tray.RequestTrayFactory"%>
<%@page import="com.eng.framework.tray.DhitechRequestTrayFactory"%>
<%@page import="com.eng.adminBoard.cmd.AdminBoardCmd"%>
<%@ include file="/include/aclCheck.jsp" %>

<%


	request.setCharacterEncoding("utf-8");
 	RequestTrayFactory requestFactory  = new DhitechRequestTrayFactory();
 	Tray reqTray = requestFactory.getTray(request);


	AdminBoardCmd command = new AdminBoardCmd(reqTray, request, response);
	ArrayList arrayList = (ArrayList)request.getAttribute("result");
	
	Tray countTray = (Tray)arrayList.get(0);
	Tray rsTray = (Tray)arrayList.get(1);

%>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>DataTables | Gentelella</title>

    <!-- Bootstrap -->
    <link href="../vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="../vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">  
    <!-- NProgress -->
    <link href="../vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- iCheck -->
    <link href="../vendors/iCheck/skins/flat/green.css" rel="stylesheet">
    <!-- Datatables -->
    <link href="../vendors/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
    <link href="../vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="../build/css/custom.min.css" rel="stylesheet">
    
    
    <script type="text/javascript">
    	
    	function excelDown(){
    		window.open("./indexExcel.jsp", "fullexcel", "toolbar=no, resizable=no, width=1, height=1 left=1 top=1");
    	}
    
    
    function goDetail(idx){
		var frm = document.frm;
		var popOption = "scrollbars=auto,toolbar=no,resizable=no,width=650,height=600,left=0,top=0";
		var pop = window.open('', 'goDetail', popOption); 			
		frm.action="./registerPop.jsp?idx="+idx;
		frm.target="goDetail";
		frm.submit();
	}
	
	
    </script>
    
    
  </head>

  <body class="nav-md">
  	
	<form name="frm" method="post" action="">
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
              <span>English</span>
            </div>

            <div class="clearfix"></div> 

            <!-- menu profile quick info
            <div class="profile clearfix">
              <div class="profile_pic">
                <img src="images/img.jpg" alt="..." class="img-circle profile_img">
              </div>
              <div class="profile_info">
                <span>Welcome,</span>
                <h2>John Doe</h2>
              </div>
            </div>
            /menu profile quick info -->

            <br />

            <!-- sidebar menu -->
            <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
              <div class="menu_section">
                <h3>Hi!<%=session.getAttribute("username")%></h3>
                <ul class="nav side-menu">
                	
                  <li><a><i class="fa fa-table"></i> Tables <span class="fa fa-chevron-down"></span></a>
                    <ul class="nav child_menu">
                      <li><a href="index.jsp">Tables</a></li>
                      <li><a href="register.jsp">Regist</a></li>
                    </ul>
                  </li>
                 
                </ul>
              </div>
              
            </div>
           
          </div>
        </div>

        <!-- top navigation -->
        <%@include file="/include/topengnav.jsp"%>
        <!-- /top navigation -->

        <!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="clearfix"></div>

            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Data </h2>
                    
                    <div class="clearfix"></div>
                  </div>
                  <div class="fa-hover col-md-3 col-sm-4 col-xs-12"><a href="javascript:excelDown();"><i class="fa fa-file-excel-o"></i> Excel Down</a>
                  </div>
                  <div class="x_content">                   
                    <table id="datatable" class="table table-striped table-bordered">
                      <thead>
                        <tr>
                          <th>Last name</th>
                          <th>First name</th>
                          <th>Date of birth</th>
                          <th>Gender</th>
                          <th>Age</th>
                          <th>Nationality</th>
                          <th>Contact No.in your country</th>                          
                        </tr>
                      </thead>


                      <tbody>
                      	
                      	<%
													if(rsTray.getRowCount() != 0){
														for (int i = 0 ; i < rsTray.getRowCount() ; i++) {
															String s1 = rsTray.getString("check_date_form", i);
												%>
                      	
                        <tr style="cursor:pointer" onclick="javascript:goDetail('<%=rsTray.getString("idx", i)%>')">
                          <td><%=rsTray.getString("name", i)%></td>
                          <td><%=rsTray.getString("gender", i)%></td>
                          <td><%=rsTray.getString("email", i)%></td>
                          <td><%=rsTray.getString("etc1", i)%></td>
                          <td><%=rsTray.getString("etc2", i)%></td>
                          <td><%=rsTray.getString("etc3", i)%></td>
                          <td><%=rsTray.getString("etc4", i)%></td>
                        </tr>
                        
                        
                        <%
														}
													}else{
												%>


												<%
													}
												%>

                      </tbody>
                    </table>
                  </div>
                </div>
              </div>

            </div>
          </div>
        </div>
        <!-- /page content -->

         <!-- footer content -->
				<%@include file="/include/footeng.jsp"%>
        <!-- /footer content -->
      </div>
    </div>

    <!-- jQuery -->
    <script src="../vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="../vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="../vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="../vendors/nprogress/nprogress.js"></script>
    <!-- iCheck -->
    <script src="../vendors/iCheck/icheck.min.js"></script>
    <!-- Datatables -->
    <script src="../vendors/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="../vendors/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="../vendors/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="../vendors/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
    <script src="../vendors/datatables.net-buttons/js/buttons.flash.min.js"></script>
    <script src="../vendors/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="../vendors/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="../vendors/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
    <script src="../vendors/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
    <script src="../vendors/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="../vendors/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    <script src="../vendors/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
    <script src="../vendors/jszip/dist/jszip.min.js"></script>
    <script src="../vendors/pdfmake/build/pdfmake.min.js"></script>
    <script src="../vendors/pdfmake/build/vfs_fonts.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="../build/js/custom.min.js"></script>
	</form>
  </body>
</html>