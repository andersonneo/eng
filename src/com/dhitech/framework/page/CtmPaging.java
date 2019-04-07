
package com.dhitech.framework.page;

import java.text.*;

import org.jaxen.function.CeilingFunction;

public class CtmPaging
{
    private int total = 0;
    private int start = 0;
    private int end = 0;
    private int pageTotal = 0;


    public String className                                     = "CtmPaging";
    public String methodName                                    = "";
    public String message                                       = "";

    public String getPageInfo(int pageNum, int total, int pageSize)
    {

        String return_value = "";
        NumberFormat numComma = NumberFormat.getNumberInstance();

        pageTotal = (total-1) / pageSize + 1;
        int curPage = pageNum + 1;


        return_value = "<img src=\"/img/ic/ic_arrow.gif\"> Pages : "+curPage+" / "+numComma.format(pageTotal);




        return return_value;
    }
    
    public String getHtml_detail(int pageNum, int total, int pageSize, int paging){

       pageTotal = (total-1) / pageSize;							//전체 페이지 수	

       int pageGroupStart = (int)(pageNum/paging)*paging;			//현재 그룹의 시작 페이지
       int pageGroupEnd = pageGroupStart+paging;					//현재 그룹의 마지막 페이지
       if(pageGroupEnd > pageTotal) pageGroupEnd = pageTotal + 1;	//페이지 초과 안되게 조건 걸기	

       StringBuffer html = new StringBuffer();

       html.append("<dl><dd>");

       if(pageNum!=0){
           html.append("<a href=\"javascript:jsPaging('0')\"><img src=\"/images/btn/paging_prev1_nor.gif\" alt=\"처음 으로 이동\" /></a>");
       }
       else {
    	   html.append("<a href=\"#\"><img src=\"/images/btn/paging_prev1_nor.gif\" alt=\"처음 으로 이동\" /></a>");
       }
       //if (pageNum - paging >= 0){
       if (pageNum != 0 && (pageNum - paging) >= 0){
    	   int prePage = (pageNum / paging) * paging-1 ;
           html.append("<a href=\"javascript:jsPaging('"+ prePage +"')\"><img src=\"/images/btn/paging_prev2_nor.gif\" alt=\"이전\" /></a>");
       }else {
    	   html.append("<a href=\"#\"><img src=\"/images/btn/paging_prev2_nor.gif\" alt=\"이전\" /></a>");
       }

       html.append("</dd><dd class=\"num\">");

       for(int k=pageGroupStart;  k<pageGroupEnd; k++){
           if (k==pageNum){
               html.append("<a href=\"#\"><b>"+ (k+1) +"</b></a>");
           }else{
               html.append("<a href=\"javascript:jsPaging('"+k+"')\">"+ (k+1) +"</a>");
           } // end if
       } // end for
       html.append("</dd><dd>");
       
       //if ((pageGroupStart + paging) < (pageTotal+1)){
       if (pageNum < (pageTotal-paging)){
    	   int nextPage = (pageNum / paging) * paging + paging ;
           html.append("<a href=\"javascript:jsPaging('"+ nextPage +"')\"><img src=\"/images/btn/paging_next2_nor.gif\" alt=\"다음\" /></a>");
       }else {
    	   html.append("<a href=\"#\"><img src=\"/images/btn/paging_next2_nor.gif\" alt=\"다음\" /></a>");           
       }

       if(pageTotal != 0 && pageNum+1 != pageTotal+1){           
           html.append("<a href=\"javascript:jsPaging('"+ pageTotal +"')\"><img src=\"/images/btn/paging_next1_nor.gif\" alt=\"10 페이지 다음으로 이동\" /></a>");
       }else{
    	   html.append("<a href=\"#\"><img src=\"/images/btn/paging_next1_nor.gif\" alt=\"10 페이지 다음으로 이동\" /></a>");
       }
       
       html.append("</dd></dl>");
       return html.toString();
   }
    /**
     * 
     * @param pageNum   현재 페이지 번호 (초기값 = 0)
     * @param total     전체 게시글 수
     * @param pageSize  페이지에 보여지는 게시글 수 
     * @param paging    게시판 페이징 수
     * @return
     * 
     */
    
 
	
    public String getHtml(int pageNum, int total, int pageSize, int paging){

        pageTotal = (total-1) / pageSize;							//전체 페이지 수
        int currentGroup = pageNum/paging;							//현재 블럭 0 
        int totalGroup = pageTotal/paging;							//블럭에 총 갯수 1

        int pageGroupStart = (int)(pageNum/paging)*paging;			//현재 페이지에 보여질 페이징 스타트 번호
        int pageGroupEnd = pageGroupStart+paging;					//현재 페이지에 보여질 페이징 마지막 번호		
        if(pageGroupEnd > pageTotal) pageGroupEnd = pageTotal + 1;	//페이지 초과 안되게 조건

        StringBuffer html = new StringBuffer();
 
        

        //현재 블럭이 0 또는 이하일때 아무것도 표시하지 않고 
        //현재 블럭이 0 이상일때 처음페이지로 이동, 10페이지 이전 아이콘이 보인다.
        //System.out.println("currentGroup: "+currentGroup);
        if(currentGroup <=0){
        	//html.append("<a href=\"#\"><button type=\"button\" class=\"btn btn-default\">&lt; &lt;</button></a>");
        	html.append("<button type=\"button\" class=\"btn btn-default\">&lt;</button> ");        	
        }else{
        	int prePage = (pageNum / paging) * paging-1 ;
        	//html.append("<a href=\"javascript:jsPaging('0')\"><button type=\"button\" class=\"btn btn-default\">&lt; &lt;</button></a>");
        	//html.append("<a href=\"javascript:jsPaging('"+ prePage +"')\"><button type=\"button\" class=\"btn btn-default\">&lt;</button></a>");
        	html.append("<button type=\"button\" class=\"btn btn-default\" onclick=\"javascript:go_paging('0')\">&lt;&lt;</button> ");
        	html.append("<button type=\"button\" class=\"btn btn-default\" onclick=\"javascript:go_paging('"+ prePage +"')\">&lt;</button> ");
        	
        }
        
        for(int k=pageGroupStart;  k<pageGroupEnd; k++){
            if (k==pageNum){
            	html.append("<button type=\"button\" class=\"btn btn-primary\">" + (k+1)+ "</button> ");
            }else{

            	html.append("<button type=\"button\" class=\"btn btn-default\" onclick=\"javascript:go_paging('"+ (k) +"');\">" + (k+1)+ "</button> ");
            } // end if
            
        } // end for
        
        
        //현재 블럭과 전체 블럭수가 같거나 현재 블럭수가 더 크다면 아무것도 보이지 않고
        //그것이 아니라면 10페이지 다음 버튼과 마지막 페이지로 이동 버튼이 보인다.
        if(currentGroup >= totalGroup){
        	html.append(" <button type=\"button\" class=\"btn btn-default\">&gt;</button>");
        }else{
        	int nextPage = (pageNum / paging) * paging + paging ;        	
        	html.append(" <button type=\"button\" class=\"btn btn-default\" onclick=\"javascript:go_paging('"+ nextPage +"')\">&gt;</button>");
        	html.append(" <button type=\"button\" class=\"btn btn-default\" onclick=\"javascript:go_paging('"+ pageTotal +"')\">&gt;&gt;</button>");
        }
        return html.toString();
    }

    public String getHtml2(int pageNum, int total, int pageSize, int paging){

        pageTotal = (total-1) / pageSize;

       int pageGroupStart = (int)(pageNum/paging)*paging;
       int pageGroupEnd = pageGroupStart+paging;
       if(pageGroupEnd > pageTotal) pageGroupEnd = pageTotal + 1;

       StringBuffer html = new StringBuffer();


       if(pageNum!=0){

           html.append("<a href=\"javascript:jsPaging('0')\"><img src=\"/imgs/bulletin_pagefirst.gif\" border=\"0\" align=\"absmiddle\" alt=\"\"></a>");
       }
       else {

           html.append("<img src=\"/imgs/bulletin_pagefirst.gif\" border=\"0\" align=\"absmiddle\" alt=\"\">");
       }

       html.append("&nbsp;");


       //if (pageNum - paging >= 0){
       if (pageNum != 0 && (pageNum - paging) >= 0){
       	int prePage = (pageNum / paging) * paging-1 ;

               html.append("<a href=\"javascript:jsPaging('"+ prePage +"')\"><img src=\"/imgs/bulletin_pre.gif\" border=\"0\" align=\"absmiddle\" alt=\"\"></a>");
       }else {

           html.append("<img src=\"/imgs/bulletin_pre.gif\" border=\"0\" align=\"absmiddle\" alt=\"\">");
       }

       html.append("&nbsp;&nbsp;");

       for(int k=pageGroupStart;  k<pageGroupEnd; k++){
       	if (k!=0) {
       		html.append(" | ");
       	}
           if (k==pageNum){
               html.append("<strong><span class=\"skyblue\">"+ (k+1) +"</span></strong>");
           }else{
               html.append("<a href=\"javascript:jsPaging('"+k+"')\">"+ (k+1) +"</a>");
           } // end if
           if(pageGroupEnd - k > 1)
           {
               //html.append("&nbsp;&nbsp;/&nbsp;&nbsp;");
               html.append("&nbsp;");
           }
       } // end for
       html.append("&nbsp;&nbsp;");


       //if ((pageGroupStart + paging) < (pageTotal+1)){
       if (pageNum < (pageTotal-paging)){
       	int nextPage = (pageNum / paging) * paging + paging ; 

           html.append("<a href=\"javascript:jsPaging('"+ nextPage +"')\"><img src=\"/imgs/bulletin_pagenext.gif\" border=\"0\" align=\"absmiddle\" alt=\"\"></a>");
       }else {

           html.append("<img src=\"/imgs/bulletin_pagenext.gif\" border=\"0\" align=\"absmiddle\" alt=\"\">");
       }

       html.append("&nbsp;");


       if(pageTotal != 0 && pageNum+1 != pageTotal+1){

           html.append("<a href=\"javascript:jsPaging('"+ pageTotal +"')\"><img src=\"/imgs/bulletin_pagelast.gif\" border=\"0\" align=\"absmiddle\" alt=\"\"></a>");
       }else{

           html.append("<img src=\"/imgs/bulletin_pagelast.gif\" border=\"0\" align=\"absmiddle\" alt=\"\">");
       }

       //message                                             = "\n" + "    html = ("+html.toString()+" ) ";

       /*
       System.out.println("-------------------------------------------------------------------------");
       System.out.println(html.toString());
       System.out.println("-------------------------------------------------------------------------");
       */
       return html.toString();
   } 
    
    public String getHtml_user(int pageNum, int total, int pageSize, int paging){

        pageTotal = (total-1) / pageSize;

       int pageGroupStart = (int)(pageNum/paging)*paging;
       int pageGroupEnd = pageGroupStart+paging;
       if(pageGroupEnd > pageTotal) pageGroupEnd = pageTotal + 1;

       StringBuffer html = new StringBuffer();


       if(pageNum!=0){

           html.append("<a href=\"javascript:jsPaging('0')\"><img src=\"/imgs/bulletin_pagefirst.gif\" border=\"0\" align=\"absmiddle\" alt=\"\"></a>");
       }
       else {

           html.append("<img src=\"/imgs/bulletin_pagefirst.gif\" border=\"0\" align=\"absmiddle\" alt=\"\">");
       }

       html.append("&nbsp;");


       //if (pageNum - paging >= 0){
       if (pageNum != 0 && (pageNum - paging) >= 0){
       	int prePage = (pageNum / paging) * paging-1 ;

               html.append("<a href=\"javascript:jsPaging('"+ prePage +"')\"><img src=\"/imgs/bulletin_pre.gif\" border=\"0\" align=\"absmiddle\" alt=\"\"></a>");
       }else {

           html.append("<img src=\"/imgs/bulletin_pre.gif\" border=\"0\" align=\"absmiddle\" alt=\"\">");
       }

       html.append("&nbsp;&nbsp;");

       for(int k=pageGroupStart;  k<pageGroupEnd; k++){
       	if (k!=0) {
       		html.append(" | ");
       	}
           if (k==pageNum){
               html.append("<strong><span class=\"skyblue\">"+ (k+1) +"</span></strong>");
           }else{
               html.append("<a href=\"javascript:jsPaging('"+k+"')\">"+ (k+1) +"</a>");
           } // end if
           if(pageGroupEnd - k > 1)
           {
               //html.append("&nbsp;&nbsp;/&nbsp;&nbsp;");
               html.append("&nbsp;");
           }
       } // end for
       html.append("&nbsp;&nbsp;");


       //if ((pageGroupStart + paging) < (pageTotal+1)){
       if (pageNum < (pageTotal-paging)){
       	int nextPage = (pageNum / paging) * paging + paging ; 

           html.append("<a href=\"javascript:jsPaging('"+ nextPage +"')\"><img src=\"/imgs/bulletin_pagenext.gif\" border=\"0\" align=\"absmiddle\" alt=\"\"></a>");
       }else {

           html.append("<img src=\"/imgs/bulletin_pagenext.gif\" border=\"0\" align=\"absmiddle\" alt=\"\">");
       }

       html.append("&nbsp;");


       if(pageTotal != 0 && pageNum+1 != pageTotal+1){

           html.append("<a href=\"javascript:jsPaging('"+ pageTotal +"')\"><img src=\"/imgs/bulletin_pagelast.gif\" border=\"0\" align=\"absmiddle\" alt=\"\"></a>");
       }else{

           html.append("<img src=\"/imgs/bulletin_pagelast.gif\" border=\"0\" align=\"absmiddle\" alt=\"\">");
       }

       //message                                             = "\n" + "    html = ("+html.toString()+" ) ";


       return html.toString();
   } 

    public int getStart(int pageNum, int pageSize){

        int start = 0;

        start = pageNum * pageSize;

        return start;
    }

    public int getEnd(int start, int pageSize){

        int end = 0;

        end = start + pageSize;

        return end;
    }

    public int getPageNum(String temp){

        int pageNum = 0;

        temp = (temp.equals(""))?"0":temp;
        pageNum = Integer.parseInt(temp);

        return pageNum;
    }

}
