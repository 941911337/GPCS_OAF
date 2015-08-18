/*===========================================================================+
 |   Copyright (c) 2001, 2005 Oracle Corporation, Redwood Shores, CA, USA    |
 |                         All rights reserved.                              |
 +===========================================================================+
 |  HISTORY                                                                  |
 +===========================================================================*/
package Gpcs.oracle.apps.ak.gpcs.webui;

import Gpcs.oracle.apps.ak.gpcs.server.GPCSVOImpl;

import Gpcs.oracle.apps.ak.gpcs.server.GPCSVORowImpl;

import java.io.Serializable;

import oracle.apps.fnd.common.VersionInfo;
import oracle.apps.fnd.framework.OAApplicationModule;
import oracle.apps.fnd.framework.OAException;
import oracle.apps.fnd.framework.webui.OAControllerImpl;
import oracle.apps.fnd.framework.webui.OAPageContext;
import oracle.apps.fnd.framework.webui.beans.OAWebBean;
import oracle.apps.fnd.framework.webui.beans.message.OAMessageTextInputBean;
import oracle.apps.fnd.framework.webui.beans.table.OAHGridBean;

import oracle.jbo.ViewObject;

/**
 * Controller for ...
 */
public class GpcsCO extends OAControllerImpl
{
  public static final String RCS_ID="$Header$";
  public static final boolean RCS_ID_RECORDED =
        VersionInfo.recordClassVersion(RCS_ID, "%packagename%");

  /**
   * Layout and page setup logic for a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processRequest(pageContext, webBean);
      String as= pageContext.getParameter("RONAME");
      //后期删除,仅测试
      if(as==null)
      {
          as="BQP";
      }
      OAMessageTextInputBean fieldHelloName =
      (OAMessageTextInputBean)webBean.findChildRecursive("ApplySite");
      fieldHelloName.setText(pageContext,"BQP");
      OAApplicationModule am = pageContext.getApplicationModule(webBean);
        Serializable[] params =  {as};
        Class[] classes = { String.class };
        am.invokeMethod("querySOBVO", params, classes);
        OAHGridBean hgrid = 
        (OAHGridBean)webBean.findChildRecursive("HGridRN");
        hgrid.setRootNodeText("ALL");
        

            String javaScript="JAVASCRIP:function setSelect(){" +
            "   event = event ? event : window.event; \n" + 
            "    var obj = event.srcElement ? event.srcElement : event.target; \n" + 
            "    var string='';\n" + 
            "    var string1='';\n" +
            "if(obj.innerHTML==\"Select All\")\n" + 
            " {\n" +  
            "         submitForm('DefaultFormName',1,{'evtSrcRowIdx':'','sob':'','evtSrcRowId':'',event:'Test1',source:'','sobt':'0','out':'0','check':'0'});return true;"+ 
            " }" +
            "if(obj.innerHTML==\"Select None\")\n" + 
            " {\n" + 
            "         submitForm('DefaultFormName',1,{'evtSrcRowIdx':'','sob':'','evtSrcRowId':'',event:'Test1',source:'','sobt':'0','out':'1','check':'0'});return true;" + 
            " }" +    
            " if(obj.constructor == HTMLInputElement)\n" + 
            " {\n" + 
            "         \n" + 
            "         var t=obj.parentNode.nextSibling.nextSibling.innerHTML;\n" + 
            "         var v=obj.parentNode.nextSibling.nextSibling.innerText;\n" + 
            "         var flag =obj.checked;\n" + 
            "        var t1=t.indexOf(\"margin-left:\")+12; \n" + 
            "        var t3=t.substring(t1, t1+1);\n" + 
           "        var t2;\n" + 
            "      if(t3==' ')\n"+
            "      {\n" + 
            "           t2=t.substring(t1+1, t1+3);\n" + 
            "      }\n" + 
            "     else\n" + 
            "     {\n" + 
            "          t2=t.substring(t1, t1+2);\n" + 
            "     }\n" + 
            "     if(t2=='32')\n" + 
            "     {\n" + 
            "            submitForm('DefaultFormName',1,{'evtSrcRowIdx':'','sob':'','evtSrcRowId':'',event:'Test1',source:'Checkbox','sobt':v,'out':'','check':flag});return true;\n" + 
            "            \n" + 
            "     }\n" + 
            "     else\n" + 
            "     {\n" + 
            "           submitForm('DefaultFormName',1,{'evtSrcRowIdx':'','sob':'','evtSrcRowId':'',event:'Test1',source:'Checkbox','sobt':'','out':v,'check':flag});return true;\n" + 
            "     }\n" + 
            "    }}" ;
            pageContext.putJavaScriptFunction("setSelect()",javaScript); 
            hgrid.setOnClick("javascript:setSelect()");
            String js="JAVASCRIP:function js_submit()" + 
            "{\n" +
            "event = event ? event : window.event;" + 
            " var obj = event.srcElement ? event.srcElement : event.target;" +
            "if(obj.innerHTML==\"Select All\" ||obj.innerHTML==\"Select None\")\n" + 
            " {\n" + 
            "         obj.href=\"#\"" + 
            " }" + 
            
            "}";
            pageContext.putJavaScriptFunction("js_submit()",js); 
            hgrid.setOnMouseOver("javascript:js_submit()");
  }

  /**
   * Procedure to handle form submissions for form elements in
   * a region.
   * @param pageContext the current OA page context
   * @param webBean the web bean corresponding to the region
   */
  public void processFormRequest(OAPageContext pageContext, OAWebBean webBean)
  {
    super.processFormRequest(pageContext, webBean);
    
      String event= pageContext.getParameter(EVENT_PARAM);
      OAApplicationModule am = pageContext.getApplicationModule(webBean);
      String ItemNo = pageContext.getParameter("ItemNo");
      System.out.println(event);
      if(event.equals("query"))
      {
          if(ItemNo.equals("")||ItemNo==null)
          {
              String message = "Item NO. can not be empty! ";
              throw new OAException(message,OAException.INFORMATION);

          }
          System.out.println("countinue");
          Serializable[] params =  {ItemNo};
          Class[] classes = { String.class };
          am.invokeMethod("queryGpcs", params, classes);
      }
      if(event.equals("clear"))
      {
          am.invokeMethod("clearVO");
          OAMessageTextInputBean fieldHelloName =
          (OAMessageTextInputBean)webBean.findChildRecursive("ItemNo");
          fieldHelloName.setText(pageContext,"");
      }
      if(event.equals("Test1"))
      {
          String sobt=pageContext.getRawParameter("sobt").trim();
          String out=pageContext.getRawParameter("out").trim();
          String check=pageContext.getRawParameter("check").trim();
          if( sobt.equals("0"))
          {
             String s="";
             if(out.equals("0"))
             {
                s="Y";
             }
             else{
                 s="N";
             }
              Serializable[] params =  {s};
              am.invokeMethod("JsSetALL",params);
          } 
          if( !sobt.equals("")&&!sobt.equals("0"))
          {
              Serializable[] params =  {sobt,check};
              am.invokeMethod("JsSetSOB",params); 
          }  
          if( !out.equals("")&&sobt.equals(""))
          {
              Serializable[] params =  {out,check};
              am.invokeMethod("JsSetOU",params); 
          }
      }
      if(event.equals("apply"))
      {
          GPCSVOImpl vo= (GPCSVOImpl)am.findViewObject("GPCSVO1");
          GPCSVORowImpl GPCSRow=(GPCSVORowImpl)vo.getCurrentRow();
          String LongDescription=GPCSRow.getLongdescription();
          if(ItemNo.equals("")||ItemNo==null)
          {
              String message = "Please query first! ";
              throw new OAException(message,OAException.INFORMATION);
          }
          if(LongDescription.equals("")||LongDescription==null)
          {
              String message = "Please input long description ";
              throw new OAException(message,OAException.INFORMATION);
          }
          String as= pageContext.getParameter("RONAME");
          String CountryOfOrigin= pageContext.getParameter("CountryOfOrigin");
          String JanCode= pageContext.getParameter("JANCode");
          String HSTCCCode= pageContext.getParameter("HSTCCCode");
          String IndiaVATProductGroup= pageContext.getParameter("IndiaVATProductGroup");
          String userid=pageContext.getUserId()+"";
          String LocalModel=pageContext.getParameter("LocalModel");
          
          //后期删除,仅测试
          if(as==null)
          {
              as="BQP";
          }
          Serializable[] params =  {as,CountryOfOrigin,JanCode,HSTCCCode,IndiaVATProductGroup,userid,LocalModel,LongDescription};
          am.invokeMethod("ApplytoOracle",params); 
      }

  }

}

