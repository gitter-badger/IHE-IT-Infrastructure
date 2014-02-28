<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:xhtml="http://www.w3.org/1999/xhtml" 
                xmlns:ds="http://www.w3.org/2000/09/xmldsig#"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <xsl:output method="html" indent="yes" version="4.01" encoding="utf-8" doctype-public="-//W3C//DTD HTML 4.01//EN"/>
    <xsl:variable name="title">
         <xsl:value-of select="sheet/sheetType"/>
    </xsl:variable>
	
	<!-- 病歷號 -->
	<xsl:variable name="patientId">
        <xsl:value-of select="sheet/patientId"/>
    </xsl:variable>
	<!-- 病患姓名 -->
    <xsl:variable name="patientName">
        <xsl:value-of select="sheet/patientName2"/>
    </xsl:variable>
	<!-- 身分證字號 -->
    <xsl:variable name="patientCardId">
        <xsl:value-of select="sheet/patientIdNo"/>
    </xsl:variable>
	<!-- 病患性別 -->
    <xsl:variable name="patientSex">
        <xsl:if test="sheet/patientSex">
            <xsl:variable name="sex" select="sheet/patientSex"/>
            <xsl:choose>
                <xsl:when test="$sex='M'">男</xsl:when>
                <xsl:when test="$sex='F'">女</xsl:when>
                <xsl:when test="$sex='O'">其它</xsl:when>
            </xsl:choose>
        </xsl:if>
    </xsl:variable>
	<!-- 病患生日 -->
    <xsl:variable name="patientBirthday">
        <xsl:call-template name="formateDate">
            <xsl:with-param name="date" select="sheet/patientBirthDate"/>
            <xsl:with-param name="type" select="03"/>
        </xsl:call-template>
    </xsl:variable>
	<!-- 開單日期 -->
    <xsl:variable name="orderDate">
        <xsl:call-template name="formateDate">
            <xsl:with-param name="date" select="sheet/orderDate"/>
            <xsl:with-param name="type" select="03"/>
        </xsl:call-template>
    </xsl:variable>
	<!-- 報告日期時間 -->
    <xsl:variable name="reportDate">
        <xsl:call-template name="formateDate">
            <xsl:with-param name="date" select="sheet/recordApprovedDateTime"/>
            <xsl:with-param name="type" select="02"/>
        </xsl:call-template>
    </xsl:variable>
	<!-- 診別 -->
    <xsl:variable name="patientType">
        <xsl:if test="sheet/patientType">
            <xsl:variable name="type" select="sheet/patientType"/>
            <xsl:choose>
                <xsl:when test="$type='O'">門診</xsl:when>
                <xsl:when test="$type='I'">住院</xsl:when>
                <xsl:when test="$type='E'">急診</xsl:when>
            </xsl:choose>
        </xsl:if>
    </xsl:variable>
	 
    <xsl:variable name="componentBegin">
        <script language="javascript"><![CDATA[                       
            document.write('<div style="width: 600px; word-wrap: break-word;"><pre style="width: 600px; overflow:hidden; font-size: 10pt;"><font class="fontEng">');
        ]]></script>
    </xsl:variable>
    
    <xsl:variable name="componentEnd">
        <script language="javascript"><![CDATA[                       
            document.write('</font></pre></div>');
        ]]></script>
    </xsl:variable>
<!--
Default
20101225000000 -> 20101225
201012250000   -> 20101225
Type
01. yyyyMMddhhmmss -> 民國yyy年月日
02. yyyyMMddhhmmss -> yyyy/MM/dd hh:mm:ss
03. yyyyMMddhhmmss -> yyyy/MM/dd
04. yyyyMMddhhmmss -> hh:mm:ss
for 榮總
05. yyyyMMddhhmmss -> MM/dd/yy hhmm
06. yyyyMMddhhmmss -> 民國yyy/MM/dd hhmm
new Type 2011 08

07. hhmmss -> hh:mm:ss
08. yyyyMMddhhmmss -> yyyy.MM.dd
09. yyyyMMddhhmmss -> yyyy/MM/dd hh:mm
-->
    <xsl:template name="formateDate">
        <xsl:param name="date"/>
        <xsl:param name="type"/>
        <xsl:variable name="aYear" select="substring($date,1,4)"/>
        <xsl:variable name="aMonth" select="substring($date,5,2)"/>
        <xsl:variable name="aDay" select="substring($date,7,2)"/>
        <xsl:variable name="aHour" select="substring($date,9,2)"/>
        <xsl:variable name="aMin" select="substring($date,11,2)"/>
        <xsl:variable name="aSec" select="substring($date,13,2)"/>
        <xsl:variable name="bHour" select="substring($date,1,2)"/>
        <xsl:variable name="bMin" select="substring($date,3,2)"/>
        <xsl:variable name="bSec" select="substring($date,5,2)"/>
        <xsl:variable name="LocalYear" select="$aYear - 1911"/>        
        <xsl:choose>
            <xsl:when test="$type = '01'">
                <xsl:choose>
                    <xsl:when test="substring($date,1,8) = ''"></xsl:when>
                    <xsl:when test="substring($date,1,8) = '00000000'"></xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$LocalYear"/>
                        <xsl:text>年</xsl:text>
                        <xsl:value-of select="$aMonth"/>
                        <xsl:text>月</xsl:text>
                        <xsl:value-of select="$aDay"/>
                        <xsl:text>日</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="$type = '02'">
                <xsl:choose>
                    <xsl:when test="substring($date,1,8) = ''"></xsl:when>
                    <xsl:when test="substring($date,1,8) = '00000000'"></xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$aYear"/>
                        <xsl:text>/</xsl:text>
                        <xsl:value-of select="$aMonth"/>
                        <xsl:text>/</xsl:text>
                        <xsl:value-of select="$aDay"/>
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:choose>
                    <xsl:when test="substring($date,9,2) = ''"></xsl:when>
                    <xsl:when test="substring($date,11,2) = ''"></xsl:when>
                    <!--
                    <xsl:when test="substring($date,9,4) = '0000'"></xsl:when>
                    <xsl:when test="substring($date,9,6) = '000000'"></xsl:when>
                    -->
                    <xsl:when test="substring($date,13,2) = ''">
                        <xsl:text>  </xsl:text>
                        <xsl:value-of select="$aHour"/>
                        <xsl:text>:</xsl:text>
                        <xsl:value-of select="$aMin"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>  </xsl:text>
                        <xsl:value-of select="$aHour"/>
                        <xsl:text>:</xsl:text>
                        <xsl:value-of select="$aMin"/>
                        <xsl:text>:</xsl:text>
                        <xsl:value-of select="$aSec"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="$type = '03'">
                <xsl:choose>
                    <xsl:when test="substring($date,1,8) = ''"></xsl:when>
                    <xsl:when test="substring($date,1,8) = '00000000'"></xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$aYear"/>
                        <xsl:text>/</xsl:text>
                        <xsl:value-of select="$aMonth"/>
                        <xsl:text>/</xsl:text>
                        <xsl:value-of select="$aDay"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="$type = '04'">
                <xsl:choose>
                    <xsl:when test="substring($date,9,2) = ''"></xsl:when>
                    <xsl:when test="substring($date,11,2) = ''"></xsl:when>
                    <!--
                    <xsl:when test="substring($date,9,4) = '0000'"></xsl:when>
                    <xsl:when test="substring($date,9,6) = '000000'"></xsl:when>
                    -->
                    <xsl:when test="substring($date,13,2) = ''">
                        <xsl:text>  </xsl:text>
                        <xsl:value-of select="$aHour"/>
                        <xsl:text>:</xsl:text>
                        <xsl:value-of select="$aMin"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>  </xsl:text>
                        <xsl:value-of select="$aHour"/>
                        <xsl:text>:</xsl:text>
                        <xsl:value-of select="$aMin"/>
                        <xsl:text>:</xsl:text>
                        <xsl:value-of select="$aSec"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="$type = '05'">
                <xsl:choose>
                    <xsl:when test="substring($date,1,8) = ''"></xsl:when>
                    <xsl:when test="substring($date,1,8) = '00000000'"></xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$aMonth"/>
                        <xsl:text>/</xsl:text>
                        <xsl:value-of select="$aDay"/>
                        <xsl:text>/</xsl:text>
                        <xsl:value-of select="$aYear - 2000"/>
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:choose>
                    <xsl:when test="substring($date,9,2) = ''"></xsl:when>
                    <xsl:when test="substring($date,11,2) = ''"></xsl:when>
                    <!--
                    <xsl:when test="substring($date,9,4) = '0000'"></xsl:when>
                    <xsl:when test="substring($date,9,6) = '000000'"></xsl:when>
                    -->
                    <xsl:otherwise>
                        <xsl:text>  </xsl:text>
                        <xsl:value-of select="$aHour"/>
                        <xsl:value-of select="$aMin"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="$type = '06'">
                <xsl:choose>
                    <xsl:when test="substring($date,1,8) = ''"></xsl:when>
                    <xsl:when test="substring($date,1,8) = '00000000'"></xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$LocalYear"/>
                        <xsl:text>/</xsl:text>
                        <xsl:value-of select="$aMonth"/>
                        <xsl:text>/</xsl:text>
                        <xsl:value-of select="$aDay"/>
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:choose>
                    <xsl:when test="substring($date,9,2) = ''"></xsl:when>
                    <xsl:when test="substring($date,11,2) = ''"></xsl:when>
                    <!--
                    <xsl:when test="substring($date,9,4) = '0000'"></xsl:when>
                    <xsl:when test="substring($date,9,6) = '000000'"></xsl:when>
                    -->
                    <xsl:otherwise>
                        <xsl:text>  </xsl:text>
                        <xsl:value-of select="$aHour"/>
                        <xsl:value-of select="$aMin"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="$type = '07'">
                <xsl:choose>
                    <xsl:when test="substring($date,1,6) = ''"></xsl:when>
                    <xsl:when test="substring($date,5,2) = ''">
                        <xsl:text>  </xsl:text>
                        <xsl:value-of select="$bHour"/>
                        <xsl:text>:</xsl:text>
                        <xsl:value-of select="$bMin"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text>  </xsl:text>
                        <xsl:value-of select="$bHour"/>
                        <xsl:text>:</xsl:text>
                        <xsl:value-of select="$bMin"/>
                        <xsl:text>:</xsl:text>
                        <xsl:value-of select="$bSec"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="$type = '08'">
                <xsl:choose>
                    <xsl:when test="substring($date,1,8) = ''"></xsl:when>
                    <xsl:when test="substring($date,1,8) = '00000000'"></xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$aYear"/>
                        <xsl:text>.</xsl:text>
                        <xsl:value-of select="$aMonth"/>
                        <xsl:text>.</xsl:text>
                        <xsl:value-of select="$aDay"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="$type = '09'">
                <xsl:choose>
                    <xsl:when test="substring($date,1,8) = ''"></xsl:when>
                    <xsl:when test="substring($date,1,8) = '00000000'"></xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$aYear"/>
                        <xsl:text>/</xsl:text>
                        <xsl:value-of select="$aMonth"/>
                        <xsl:text>/</xsl:text>
                        <xsl:value-of select="$aDay"/>
                    </xsl:otherwise>
                </xsl:choose>
                <xsl:choose>
                    <xsl:when test="substring($date,9,2) = ''"></xsl:when>
                    <xsl:when test="substring($date,11,2) = ''"></xsl:when>
                    <xsl:otherwise>
                        <xsl:text>  </xsl:text>
                        <xsl:value-of select="$aHour"/>
                        <xsl:text>:</xsl:text>
                        <xsl:value-of select="$aMin"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
	
	<xsl:template name="checkIsNa">
        <xsl:param name="importValue"/>
        <xsl:choose>
            <xsl:when test="normalize-space($importValue)='NA'"></xsl:when>
            <xsl:when test="normalize-space($importValue)='na'"></xsl:when>
            <xsl:when test="normalize-space($importValue)='Na'"></xsl:when>
            <xsl:when test="normalize-space($importValue)='N/A'"></xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$importValue"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
                
	<xsl:template match="/">
        <html>
            <head>
                <meta http-equiv="Content-Language" content="zh-tw" />
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                <title>
					<xsl:value-of select="$title"/>
                </title>
                    <script language="javascript" src="js/dtc.js"></script> 
					<script language="javascript" src="js/printSecurity.js"></script>
                    <script language="javascript"><![CDATA[
                        resizeWindow(top.window);
                        var strFrame = getParameter(topLocation, 'frame');
                        var sign = getParameter(topLocation, 'sign');
                    ]]></script>
                <style type="text/css">

                </style>
                <link rel="stylesheet" type="text/css" href="css/demo.css"/>
                <style type="text/css">
                    .fontCh {
                        font-family: 標楷體;
                    }

                    .fontEng {
                        font-family: "Times New Roman", Times, serif;
                    }
                    
                    body {
                        background-repeat:no-repeat;
                        background-position :50% 0%;
                        background-attachment: fixed;
                    }
                </style>
                <link rel="stylesheet" type="text/css" href="css/dtc/cdar2_dtc_path_cyto_print.css" media="print" />
                <style media="print">
                    body {
                        background-repeat:no-repeat;
                        background-position :50% 0%;
                        background-attachment: fixed;
                    }
                </style>
                
            </head> 
            <body>
				<table width="100%" class="table" cellspacing="0">
					<tr> 
                        <td class="td" width="8%" rowspan="4">
                            <div></div>
                        </td>
                        <td class="td" width="22%" rowspan="2">
                        </td>
                        <td class="td" width="40%" rowspan="3">
                            <center class="title">
                                <font class="fontCh">跨院醫療文件</font>
                            </center>
                        </td>
                        <td class="td" width="30%">
                        </td>
                    </tr>
                    <tr>
                        <td class="td" width="30%">
                            
                        </td>
                    </tr>
                    <tr>
                        <td class="td" rowspan="2">
                        </td>
                        <td class="td" >
                        </td>
                    </tr>
				</table>
				
                <table width="100%" cellspacing="0">
					<tr>
                        <td colspan="100">
                            <hr/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="20">
                            <span class="span">
                                <font class="fontCh">姓名：</font><xsl:value-of select="$patientName"/>
                            </span>
                        </td>
                        <td colspan="20">
                            <span class="span">
                                <font class="fontCh">病歷號：</font><xsl:value-of select="$patientId"/>
                            </span>
                        </td>
						<td colspan="20">
                            <span class="span">
                                <font class="fontCh">身分證號：</font><xsl:value-of select="$patientCardId"/>
                            </span>
                        </td>
                        <td colspan="20">
                            <span class="span">
                                <font class="fontCh">性別：</font><xsl:value-of select="$patientSex"/>
                            </span>
                        </td>
                        <td colspan="20">
                            <span class="span">
                                <font class="fontCh">出生日期：</font><xsl:value-of select="$patientBirthday"/>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="20">
                            <span class="span">
                                <font class="fontCh">申請單號：</font><xsl:value-of select="sheet/orderNo"/>
                            </span>
                        </td>
                        <td colspan="20">
                            <span class="span">
                                <font class="fontCh">開單日期：</font><xsl:value-of select="$orderDate"/>
                            </span>
                        </td>
                        <td colspan="20">
							<span class="span">
                                <font class="fontCh">病房/床位：</font><xsl:value-of select="sheet/wardNo"/>/<xsl:value-of select="sheet/bedNo"/>
                            </span>
                        </td>
                        <td colspan="20">
                        </td>
                        <td colspan="20">
                        </td>
                    </tr>
					<tr>
                        <td colspan="20">
                            <span class="span">
								<font class="fontCh">檢查存取號：</font>
							</span>
							<font class="fontEng"><xsl:value-of select="sheet/accessionNo"/></font>
                        </td>
                        <td colspan="20">
                            <span class="span">
								<font class="fontCh">檢查項目：</font>
							</span>
							<font class="fontEng"><xsl:value-of select="sheet/studyId"/></font>
                        </td>
						<td colspan="20">
                            <span class="span">
								<font class="fontCh">檢查設備種類：</font>
							</span>
							<font class="fontEng"><xsl:value-of select="sheet/modality"/></font>
                        </td>
                        <td colspan="20">
                            <span class="span">
								<font class="fontCh">PCASE門急住別：</font>
							</span>
							<font class="fontEng">
								<xsl:value-of select="$patientType"/>
							</font>
                        </td>
                    </tr>
					<tr>
                        <td colspan="100" class="td">
                            <span class="span">
                                <font class="fontCh">報告內容：</font>
                            </span>
                        </td>
                    </tr>
					<tr>
						<td colspan="5" class="td">
                        </td>
                        <td colspan="95" class="td">
							<xsl:copy-of select="$componentBegin"/>
								<xsl:value-of select="sheet/reportText"/>
							<xsl:copy-of select="$componentEnd"/>
                        </td>
                    </tr>
					<tr>
                        <td colspan="100">
                            <hr/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="50">
							<span class="span">
                                <font class="fontCh">報告確認醫師：</font>
                            </span>
                            <font class="fontCh">
                                <xsl:value-of select="sheet/reportPhysician"/>
                            </font>
                        </td>
                        <td colspan="25">
                            <span class="span">
                                <font class="fontCh">報告醫師：</font>
                            </span>
                            <font class="fontCh">
                                <xsl:value-of select="sheet/reportAuthor"/>
                            </font>
                        </td>
						<td colspan="25">
                            <span class="span">
                                <font class="fontCh">報告日期：</font>
                            </span>
                            <font class="fontCh">
                                <xsl:value-of select="$reportDate"/>
                            </font>
                        </td>
                    </tr>
                    
					<tr>
						<td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/>
						<td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/>
						<td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/>
						<td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/>
						<td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/>
						<td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/>
						<td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/>
						<td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/>
						<td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/>
						<td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/><td width="1%"/>
					</tr>
                </table>
            </body>
            
        </html>
	</xsl:template>
    
</xsl:stylesheet>