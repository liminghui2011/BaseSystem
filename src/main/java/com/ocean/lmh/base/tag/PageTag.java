package com.ocean.lmh.base.tag;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocean.lmh.base.model.vo.PaginationBean;
import com.ocean.lmh.base.util.AssertHelper;

/**
 * 
 * 分页标签 〈功能详细描述〉
 * 例子：
 * <tr>
 * <td colspan="8" form-id="PARAM" class="paginationPanel">
 * <ltPage:page pageBean="${pb}" />
 * </td>
 * </tr>
 * 
 * @since 1.0
 */


public class PageTag extends TagSupport
{
    private static Logger log = LoggerFactory.getLogger(PageTag.class);

    private static final long serialVersionUID = 3016672032438433838L;
   
    private PaginationBean<?> pageBean;

    // 临时变量
    private int current;
    private int pageCount;
    private int rowCount;
    private int pageSize;

    private int next;
    private int prev;
    private int first;
    private int last;
    @SuppressWarnings("unused")
	private int start;
    private int multiDisplayCount;
    private String url = "#";

    @Override
    public int doStartTag() throws JspException
    {
        JspWriter out = super.pageContext.getOut();
        try
        {
           
            StringBuffer sb = new StringBuffer();
            if (0 == this.pageBean.getRowCount())
            {
                sb.append("没有找到合适的数据!");
                out.write(sb.toString());
                return SKIP_BODY;
            }
            
            //初始化临时变量 
            initParam(pageBean);

            String indexUrl = null;
            String endUrl = null;
            String nextUrl = null;
            String preUrl = null;

            int _rowCount = pageCount;
            int _current = (current - 1) / multiDisplayCount + 1;
            int start = (_current - 1) * multiDisplayCount + 1;
            int end = start + multiDisplayCount - 1;
            end = end > _rowCount ? _rowCount : end;

            String line = "\r\n";

            sb.append("<div class=\"pagination pull-left\">").append(line);
            sb.append("<ul>").append(line);

            if (AssertHelper.isEmptyString(url))
            {
                indexUrl = "#";
                endUrl = "#";
                preUrl = "#";
                nextUrl = "#";
            }
            else
            {
                indexUrl = getPageUrl(url, 1, pageSize);
                endUrl = getPageUrl(url, last, pageSize);
                preUrl = getPageUrl(url, prev, pageSize);
                nextUrl = getPageUrl(url, next, pageSize);
            }
            if (current == 1)
            {
                sb.append("<li class=\"disabled\"><a href=\"#\">首页</a></li>").append(line);
                sb.append("<li class=\"disabled\"><a href=\"#\">上页</a></li>").append(line);
            }
            else
            {
                sb.append("<li page=\"" + first + "\"><a href=\"").append(indexUrl).append("\">首页</a></li>").append(line);
                sb.append("<li page=\"").append(prev).append("\"><a href=\"").append(preUrl).append("\">上页</a></li>").append(line);
            }
            for (int i = start; i <= end; ++i)
            {
                if (i == current)
                {
                    sb.append("<li page=\"").append(i).append("\" class=\"active\"><a href=\"#\">").append(i).append("</a></li>").append(line);
                }
                else
                {
                    sb.append("<li page=\"").append(i).append("\"><a href=\"").append(getPageUrl(url, i, pageSize)).append("\">")
                      .append(i).append("</a></li>").append(line);
                }
            }
            if (current == pageCount)
            {
                sb.append("<li class=\"disabled\"><a href=\"#\">下页</a></li>").append(line);
                sb.append("<li class=\"disabled\"><a href=\"#\">尾页</a></li>").append(line);
            }
            else
            {
                sb.append("<li page=\"").append(next).append("\"><a href=\"").append(nextUrl).append("\">下页</a></li>").append(line);
                sb.append("<li page=\"").append(last).append("\"><a href=\"").append(endUrl).append("\">尾页</a></li>").append(line);
            }
            sb.append("</ul>").append(line);
            sb.append("</div>").append(line);

            sb.append("<div class=\"input-prepend input-append pull-right\">").append(line);

            sb.append("<span class=\"add-on\">总共&nbsp;").append(rowCount).append("&nbsp;条记录</span>").append(line);
            sb.append("<span class=\"add-on\">第&nbsp;").append(current).append("/").append(pageCount).append("&nbsp;页</span>");
            
            /**
             * sb.append("<span class=\"add-on\">每页显示</span>").append(line);
             */
            
            sb.append("<input type=\"text\" value=\"").append(pageSize).append("\" class=\"input-mini pageSize\" style=\"text-align:center;display: none;\"/>").append(line);
            
            /**
             * sb.append("<span class=\"add-on\">条</span>").append(line);
             */
            
            sb.append("<span class=\"add-on\">转到第</span>").append(line);

            sb.append("<input type=\"text\" class=\"input-mini pageNumber\" style=\"text-align:center\" ");

            if (0 != rowCount)
            {
                sb.append("onkeyup=\"javascript:value=value.replace(/[^\\d]/g,'');").append(line);
                sb.append("if(this.value>").append(pageCount).append(" ){this.value=").append(pageCount).append("}\"").append(line);
                sb.append("onblur=\"value=value.replace(/[^\\d]/g,'')\"").append(line);
            }
            else
            {
                sb.append("disabled=\"disabled\"").append(line);
            }

            sb.append("/>").append(line);
            sb.append("<span class=\"add-on\">页</span>").append(line);
            sb.append("<button type=\"button\" class=\"btn gotoPage\">GO</button>").append(line);
            
            //
            sb.append("<input type=\"text\" id=\"goUrl\" value=\"");
            sb.append(getPageUrl(url, 1, pageSize));
            sb.append("\" style=\"display: none;\" />  ");
           
            

            sb.append("</div>").append(line);
           
            out.write(sb.toString());
        }
        catch (Exception e)
        {
            log.error("PageTag error!", e);
        }

        return SKIP_BODY;
    }

    public PaginationBean<?> getPageBean()
    {
        return pageBean;
    }

    public void setPageBean(PaginationBean<?> pageBean)
    {
        this.pageBean = pageBean;
    }

    private void initParam(PaginationBean<?> page)
    {
        this.current = page.getCurrent();
        this.first = page.getFirst();
        this.last = page.getLast();
        this.multiDisplayCount = page.getMultiDisplayCount();
        this.next = page.getNext();
        this.pageCount = page.getPageCount();
        this.pageSize = page.getPageSize();
        this.prev = page.getPrev();
        this.rowCount = page.getRowCount();
        this.start = page.getStart();
        this.url = page.getUrl();

    }

    private String getPageUrl(String url, int current, int pageSize)
    {
        if (url.indexOf("/") != -1)
        {
            url = url.substring(url.lastIndexOf("/") + 1, url.length());
        }
        if (url.indexOf("current") == -1)
        {
            if (url.endsWith(".do"))
            {
                url = url + "?" + "current=" + current + "&pageSize="
                        + pageSize;
            }
            else
            {
                url = url + "&current=" + current + "&pageSize=" + pageSize;
            }
        }
        else
        {
            String[] paramArray = url.substring(url.indexOf("?") + 1,
                    url.length()).split("&");
            StringBuffer temp = new StringBuffer(url.substring(0, url
                    .indexOf("?") + 1));
            for (String string : paramArray)
            {
                if (string.indexOf("current=") != -1)
                {
                    string = string
                            .substring(0, string.indexOf("current=") + 8)
                            + current;
                }
                if (string.indexOf("pageSize=") != -1)
                {
                    string = string.substring(0,
                            string.indexOf("pageSize=") + 9)
                            + pageSize;
                }
                temp.append(string).append("&");
            }
            url = temp.substring(0, temp.length() - 1).toString();
        }
        return url;
    }

}
