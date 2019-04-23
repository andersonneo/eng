<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.eng.framework.tray.Tray"%>
<%@ page import="com.eng.framework.tray.RequestTrayFactory"%>
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



<!DOCTYPE html>
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
    	
    	function update(gubun){
    		document.frm.action = "./registCTL.jsp?gubun="+gubun+"&idx="+<%=rsTray.getInt("idx") %>;
		    document.frm.targer = "HiddenFrame";
		    document.frm.submit();
    	}
    
    </script>
  </head>

  <body class="nav-md">
  
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
             <span>English</span>
            </div>

            <div class="clearfix"></div>

            <br />

           
           
          </div>
        </div>
         
        <!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="clearfix"></div>

             <div class="x_panel">
                  
                  <div class="x_content">
                    <br />
                    <form class="form-horizontal form-label-left" name="frm" method="post" action="">
                    	
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Name</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="name" class="form-control" value='<%=rsTray.getString("name")%>' />
                        </div>
                      </div>
                     
                     
                     
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">email</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="email" id="email" class="form-control col-md-10" value='<%=rsTray.getString("email")%>' />
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Select</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <select class="form-control" name="gender">
                            <option>select</option>
                            <option value="male"  <%if(rsTray.getString("gender").equals("male")){%> selected <%}%>> male</option>
                            <option value="female"<%if(rsTray.getString("gender").equals("male")){%> selected <%}%>> female</option>
                          </select>
                        </div>
                      </div>
                    
                    	 <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">etc1</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="etc1" id="etc1" class="form-control col-md-10" value='<%=rsTray.getString("email")%>' />
                        </div>
                      </div>
                      
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">etc2</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="etc2" id="etc2" class="form-control col-md-10" value='<%=rsTray.getString("email")%>' />
                        </div>
                      </div>
                      
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">etc3</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="etc3" id="etc3" class="form-control col-md-10" value='<%=rsTray.getString("email")%>' />
                        </div>
                      </div>
                      
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">etc4</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="etc4" id="etc4" class="form-control col-md-10" value='<%=rsTray.getString("email")%>' />
                        </div>
                      </div>
                      
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">etc5</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="etc5" id="etc5" class="form-control col-md-10" value='<%=rsTray.getString("email")%>' />
                        </div>
                      </div>
                      
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">etc6</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="etc6" id="etc6" class="form-control col-md-10" value='<%=rsTray.getString("email")%>' />
                        </div>
                      </div>
                     
                     

                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                          <button type="submit" class="btn btn-success"><a href="javascript:update('delete');">Delete</a></button>
                          <button type="submit" class="btn btn-success"><a href="javascript:update('update');">Update</a></button>
                        </div>
                      </div>

                    </form>
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


	<iFrame name="HiddenFrame" width="0" height="0"></iFrame>
  </body>
</html>