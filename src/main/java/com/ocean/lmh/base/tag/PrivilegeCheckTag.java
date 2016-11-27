package com.ocean.lmh.base.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.base.util.AssertHelper;
import com.ocean.lmh.system.model.vo.UserInfo;

public class PrivilegeCheckTag extends TagSupport {

	private static final long serialVersionUID = 2805122829695791711L;

	private static Logger log = LoggerFactory
			.getLogger(PrivilegeCheckTag.class);

	private String url = "";
	private Integer ptype;
	private String inputid = "";
	private String imgid = "";
	private String imgsrc = "";
	private String aid = "";
	private String ahref = "";
	private String aclass = "";
	private String aiconsrc = "";
	private String aname = "";
	
	private String aonclick = "";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPtype() {
		return ptype;
	}

	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}

	public String getImgid() {
		return imgid;
	}

	public void setImgid(String imgid) {
		this.imgid = imgid;
	}

	public String getInputid() {
		return inputid;
	}

	public void setInputid(String inputid) {
		this.inputid = inputid;
	}

	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getAhref() {
		return ahref;
	}

	public void setAhref(String ahref) {
		this.ahref = ahref;
	}

	public String getAclass() {
		return aclass;
	}

	public void setAclass(String aclass) {
		this.aclass = aclass;
	}

	public String getAiconsrc() {
		return aiconsrc;
	}

	public void setAiconsrc(String aiconsrc) {
		this.aiconsrc = aiconsrc;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public String getAonclick() {
		return aonclick;
	}

	public void setAonclick(String aonclick) {
		this.aonclick = aonclick;
	}

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute(AppConstant.USER_INFO);
		List<String> allowUriList = user.getAllowUriList();
		if (AssertHelper.isNotEmptyString(url))
		{
			String[] urls = url.split(",");
			for (int i = 0; i < urls.length; i++) 
			{
				if (allowUriList.contains(urls[i])) 
				{
					String str = null;
					try {
						if (ptype == 1) 
						{
							// 图片形式
							str = imgTypeButton();
							pageContext.getOut().write(str);
							return EVAL_BODY_INCLUDE;
						}
						else if (ptype == 2)
						{
							// a标签链接形式
							str = ahrefTypeOrabutton();
							pageContext.getOut().write(str);
							return EVAL_BODY_INCLUDE;
						}
						else if (ptype == 3)
						{
							//若a标签需要执行点击事件则使用包裹方式
							return EVAL_PAGE;
						}
					} 
					catch (IOException e) 
					{
						log.error("privilege tag output error", e);
						return SKIP_BODY;
					}
				}
			}
		}		
		return SKIP_BODY;
	}

	/**
	 * 生成图片形式的按钮的html代码
	 * 
	 * @return
	 */
	public String imgTypeButton() {
		StringBuffer sb = new StringBuffer();
		if (AssertHelper.isNotEmptyString(inputid)) {
			sb.append("<input id='");
			sb.append(inputid);
			sb.append("' type='hidden' name='Next_Statu' />");
		}
		sb.append("<img id='");
		sb.append(imgid);
		sb.append("' src='");
		sb.append(imgsrc);
		sb.append("' style='cursor: pointer;' />");
		return sb.toString();
	}

	/**
	 * 生成当是a标签形式的链接或按钮的时候的html代码
	 * 
	 * @return
	 */
	public String ahrefTypeOrabutton() 
	{
		StringBuffer sb = new StringBuffer();
		if (AssertHelper.isNotEmptyString(aiconsrc)) 
		{
			sb.append("<img src='");
			sb.append(aiconsrc);
			sb.append("' />");
		}
		sb.append("<a id='");
		sb.append(aid);
		sb.append("' href='");
		sb.append(ahref);
		if (AssertHelper.isNotEmptyString(aclass)) 
		{
			sb.append("' class='");
			sb.append(aclass);
		}
		sb.append("' >");		
		sb.append(aname);
		sb.append("</a>");
		return sb.toString();
	}
}
