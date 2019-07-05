<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.eng.framework.tray.Tray"%>
<%@ page import="com.eng.framework.tray.RequestTrayFactory"%>
<%@ include file="/include/aclCheck.jsp" %>

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
    	
    	function save(){
    		document.frm.action = "./registCTL.jsp?gubun=insert";
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
                      <li><a href="index.jsp">User Info</a></li>
                      <li><a href="register.jsp">User Regist</a></li>
                    </ul>
                  </li>
                 
                </ul>
              </div>
            </div>
           <!-- sidebar menu -->
           
          </div>
        </div>

				<!-- top navigation -->
        <%@include file="/include/topengnav.jsp"%>
        <!-- /top navigation -->
       
        <!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="clearfix"></div>

             <div class="x_panel">
                  
                  <div class="x_content">
                    <br />
                    <form class="form-horizontal form-label-left" name="frm" method="post" action="" enctype="multipart/form-data">
                    	
                    	
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Profile image</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="file" name="profileFile" id="profileFile" class="form-control col-md-10"/>
                        </div>
                      </div>
                    	
                    	
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Last name</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="lastName" name="lastName" class="form-control">
                        </div>
                      </div>
                     
                     
                     
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">First name</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="firstName" id="firstName" class="form-control col-md-10"/>
                        </div>
                      </div>
                      
                      
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Gender</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <select class="form-control" name="gender">
                            <option>select</option>
                            <option value="male">male</option>
                            <option value="female">female</option>
                          </select>
                        </div>
                      </div>
                    
                       <div class="form-group">
	                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Date of birth</label>
	                        <div class="col-md-9 col-sm-9 col-xs-12">
	                       		<input type="text" class="form-control has-feedback-left"name="birthDay" id="single_cal1" placeholder="First Name" aria-describedby="inputSuccess2Status" >
	                          <span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
	                          <span id="inputSuccess2Status" class="sr-only">(success)</span>
	                          </div>
                       </div>
                      
                      
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Age</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="age" id="age" class="form-control col-md-10"/>
                        </div>
                      </div>
                      
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Height</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="height" id="height" class="form-control col-md-10"/>
                        </div>
                      </div>
                      
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Weight</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="weight" id="weight" class="form-control col-md-10"/>
                        </div>
                      </div>
                      
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Civil status</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="civilStatus" id="civilStatus" class="form-control col-md-10"/>
                        </div>
                      </div>
                      
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Country of birth</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="countryBirth" id="countryBirth" class="form-control col-md-10"/>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Nationality</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="nationality" id="nationality" class="form-control col-md-10"/>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Occupation</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="occupation" id="occupation" class="form-control col-md-10"/>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Address abroad(No,street,)</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="street" id="street" class="form-control col-md-10"/>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Address　abroad(city,prefecture)</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="prefecture" id="prefecture" class="form-control col-md-10"/>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Address abroad(country,ZIP code)</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="zipCode" id="zipCode" class="form-control col-md-10"/>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Contact No. in your country</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="contactNoCountry" id="contactNoCountry" class="form-control col-md-10"/>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Emergency contact No.</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="emergencyContactNo" id="emergencyContactNo" class="form-control col-md-10"/>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Relationship with the contact </label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="relationshipContact" id="relationshipContact" class="form-control col-md-10"/>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">E-mail address</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="eMail" id="eMail" class="form-control col-md-10"/>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Passport No.</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="passportNo" id="passportNo" class="form-control col-md-10"/>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Date of passport expiration</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="datePassportExpiration" id="datePassportExpiration" class="form-control col-md-10"/>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Place of issue</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="placeOfIssue" id="placeOfIssue" class="form-control col-md-10"/>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Date of arrival</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="dateArrival" id="dateArrival" class="form-control col-md-10"/>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Flight No. to The Philippines</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="flightNoPhilippines" id="flightNoPhilippines" class="form-control col-md-10"/>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Date of 1st VISA expiration</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <input type="text" name="visaExpiration" id="visaExpiration" class="form-control col-md-10"/>
                        </div>
                      </div>
                      
                      
                      
                     
                     

                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                          <button type="button" class="btn btn-primary">Cancel</button>
                          <button type="reset" class="btn btn-primary">Reset</button>
                          <button type="submit" class="btn btn-success"><a href="javascript:save();">Submit</a></button>
                        </div>
                      </div>

                    </form>
                  </div>
                </div>
            

            
            
          </div>
        </div>
        <!-- /page content -->

       
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
    <!-- bootstrap-daterangepicker -->
    <script src="../vendors/moment/min/moment.min.js"></script>
    <script src="../vendors/bootstrap-daterangepicker/daterangepicker.js"></script>


	<iFrame name="HiddenFrame" width="0" height="0"></iFrame>
  </body>
</html>