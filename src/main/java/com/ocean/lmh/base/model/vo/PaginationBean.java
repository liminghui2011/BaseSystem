package com.ocean.lmh.base.model.vo;

import java.util.List;

import com.ocean.lmh.base.util.AssertHelper;

public class PaginationBean<T> {
    /**当前页码*/
	private int		current;
	 /**总共多少页*/
	private int		pageCount;
	 /**总共多少条记录*/
	private int		rowCount;
	 /**每页显示多少条记录*/
	private int		pageSize;
	
	private int		next;
	private int		prev;
	private int		first;
	private int		last;
	private int		start;
	private int		multiDisplayCount;
	private List<T>	dataList;
	private String  url = "#";

	public PaginationBean(int current, int rowCount, int pageSize, int multiDisplayCount) {
		this.current = current;
		this.rowCount = rowCount;
		this.pageSize = pageSize;
		this.multiDisplayCount = multiDisplayCount;
		this.start = (current - 1) * pageSize;
	}
	

	public PaginationBean(int current, int rowCount, int pageSize) {
		this(current, rowCount, pageSize, 5);
	}
	

	private void init() {
		if (rowCount > 0) {
			pageCount = (rowCount - 1) / pageSize + 1;
			first = 1;
			last = pageCount;
			current = current < 1 ? 1 : current;
			current = current > pageCount ? pageCount : current;
			prev = current - 1;
			prev = prev < 1 ? 1 : prev;
			next = current + 1;
			next = next > pageCount ? pageCount : next;
			start = (current - 1) * pageSize;
		} else {
			current = 0;
		}
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getPrev() {
		return prev;
	}

	public void setPrev(int prev) {
		this.prev = prev;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public String getShortDisplay() {
		if (rowCount == 0) {
			return "没有找到合适的数据！";
		}

		int _rowCount = pageCount;
		int _current = (current - 1) / multiDisplayCount + 1;
		int start = (_current - 1) * multiDisplayCount + 1;
		int end = start + multiDisplayCount - 1;
		end = end > _rowCount ? _rowCount : end;
		String line = "\r\n";
		String html = "<div class=\"pagination pagination-centered\">" + line;
		html += "<ul>" + line;
		if (current == 1) {
			html += "<li class=\"disabled\"><a href=\"#\">首页</a></li>" + line;
			html += "<li class=\"disabled\"><a href=\"#\">上页</a></li>" + line;
		} else {
			html += "<li page=\"" + first + "\"><a href=\"#\">首页</a></li>" + line;
			html += "<li page=\"" + prev + "\"><a href=\"#\">上页</a></li>" + line;
		}
		for (int i = start; i <= end; ++i) {
			if (i == current) {
				html += "<li page=\"" + i + "\" class=\"active\"><a href=\"#\">" + i + "</a></li>" + line;
			} else {
				html += "<li page=\"" + i + "\"><a href=\"#\">" + i + "</a></li>" + line;
			}
		}
		if (current == pageCount) {
			html += "<li class=\"disabled\"><a href=\"#\">下页</a></li>" + line;
			html += "<li class=\"disabled\"><a href=\"#\">尾页</a></li>" + line;
		} else {
			html += "<li page=\"" + next + "\"><a href=\"#\">下页</a></li>" + line;
			html += "<li page=\"" + last + "\"><a href=\"#\">尾页</a></li>" + line;
		}
		html += "</ul>" + line;
		html += "</div>" + line;

		return html;
	}

	public String getFullDisplay() {
		if (rowCount == 0) {
			return "没有找到合适的数据！";
		}

		int _rowCount = pageCount;
		int _current = (current - 1) / multiDisplayCount + 1;
		int start = (_current - 1) * multiDisplayCount + 1;
		int end = start + multiDisplayCount - 1;
		end = end > _rowCount ? _rowCount : end;
		String line = "\r\n";
		String html = "<div class=\"pagination pull-left\">" + line;
		html += "<ul>" + line;
		if (current == 1) {
			html += "<li pageSize=\"" + this.pageSize + "\" class=\"disabled\"><a href=\"#\">首页</a></li>" + line;
			html += "<li pageSize=\"" + this.pageSize + "\" class=\"disabled\"><a href=\"#\">上页</a></li>" + line;
		} else {
			html += "<li pageSize=\"" + this.pageSize + "\" page=\"" + first + "\"><a href=\"#\">首页</a></li>" + line;
			html += "<li pageSize=\"" + this.pageSize + "\" page=\"" + prev + "\"><a href=\"#\">上页</a></li>" + line;
		}
		for (int i = start; i <= end; ++i) {
			if (i == current) {
				html += "<li page=\"" + i + "\" pageSize=\"" + this.pageSize + "\" class=\"active\"><a href=\"#\">" + i + "</a></li>" + line;
			} else {
				html += "<li page=\"" + i + "\" pageSize=\"" + this.pageSize + "\"><a href=\"#\">" + i + "</a></li>" + line;
			}
		}
		if (current == pageCount) {
			html += "<li pageSize=\"" + this.pageSize + "\" class=\"disabled\"><a href=\"#\">下页</a></li>" + line;
			html += "<li pageSize=\"" + this.pageSize + "\" class=\"disabled\"><a href=\"#\">尾页</a></li>" + line;
		} else {
			html += "<li pageSize=\"" + this.pageSize + "\" page=\"" + next + "\"><a href=\"#\">下页</a></li>" + line;
			html += "<li pageSize=\"" + this.pageSize + "\" page=\"" + last + "\"><a href=\"#\">尾页</a></li>" + line;
		}
		html += "</ul>" + line;
		html += "</div>" + line;

		html += "<div class=\"input-prepend input-append pull-right\">" + line;
		
		html += "<span class=\"add-on\">总共" + this.rowCount + "条记录</span>"+ line;
		
		html += "<span class=\"add-on\">第" + current + "/" + pageCount + "页，每页显示</span>" + line;
		html += "<input type=\"text\" value=\"" + pageSize
				+ "\" class=\"input-mini pageSize\" style=\"text-align:center;\" disabled=\"disabled\"/>" + line;
		html += "<span class=\"add-on\">条，转到第</span>" + line;
		html += "<input type=\"text\" class=\"input-mini pageNumber\" style=\"text-align:center\" "+ line;
		
		if(0 != this.rowCount){
		    html += "onkeyup=\"javascript:value=value.replace(/[^\\d]/g,'');" + line;
		    html += "if(this.value>"+ this.pageCount +" ){this.value="+this.pageCount+"}\"" + line;
		    html += "onblur=\"value=value.replace(/[^\\d]/g,'')\"" + line;
		}else{
		    html += "disabled=\"disabled\"" + line;
		}
		
	    html += " />" + line;
		
		
		html += "<span class=\"add-on\">页</span>" + line;
		html += "<button type=\"button\" class=\"btn gotoPage\">确定</button>" + line;
		html += "</div>" + line;

		return html;
	}
	
	public String getFullDisplayWithUrl() {
	    if (rowCount == 0) {
            return "没有找到合适的数据！";
        }
	    
	    
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
	    String html = "<div class=\"pagination pull-left\">" + line;
	    html += "<ul>" + line;
	    if(AssertHelper.isEmptyString(url))
	    {
	        indexUrl = "#";
	        endUrl = "#";
	        preUrl = "#";
	        nextUrl = "#";
	    }
	    else
	    {
	        indexUrl = getPageUrl(url,1,pageSize);
	        endUrl = getPageUrl(url,last,pageSize);
	        preUrl = getPageUrl(url,prev,pageSize);
	        nextUrl = getPageUrl(url,next,pageSize);
	    }
        if (current == 1) {
            html += "<li class=\"disabled\"><a href=\"#\">首页</a></li>" + line;
            html += "<li class=\"disabled\"><a href=\"#\">上页</a></li>" + line;
        } else {
            html += "<li page=\"" + first + "\"><a href=\""+indexUrl+"\">首页</a></li>" + line;
            html += "<li page=\"" + prev + "\"><a href=\""+preUrl+"\">上页</a></li>" + line;
        }
        for (int i = start; i <= end; ++i) {
            if (i == current) {
                html += "<li page=\"" + i + "\" class=\"active\"><a href=\"#\">" + i + "</a></li>" + line;
            } else {
                html += "<li page=\"" + i + "\"><a href=\""+getPageUrl(url,i,pageSize)+"\">" + i + "</a></li>" + line;
            }
        }
        if (current == pageCount) {
            html += "<li class=\"disabled\"><a href=\"#\">下页</a></li>" + line;
            html += "<li class=\"disabled\"><a href=\"#\">尾页</a></li>" + line;
        } else {
            html += "<li page=\"" + next + "\"><a href=\""+nextUrl+"\">下页</a></li>" + line;
            html += "<li page=\"" + last + "\"><a href=\""+endUrl+"\">尾页</a></li>" + line;
        }
        html += "</ul>" + line;
        html += "</div>" + line;

        html += "<div class=\"input-prepend input-append pull-right\">" + line;
        html += "<span class=\"add-on\">第" + current + "/" + pageCount + "页，每页显示</span>" + line;
        html += "<input type=\"text\" value=\"" + pageSize
                + "\" class=\"input-mini pageSize\" style=\"text-align:center\"/>" + line;
        html += "<span class=\"add-on\">条，转到第</span>" + line;
        html += "<input type=\"text\" class=\"input-mini pageNumber\" style=\"text-align:center\"/>" + line;
        html += "<span class=\"add-on\">页</span>" + line;
        html += "<button type=\"button\" class=\"btn gotoPage\">确定</button>" + line;
        html += "</div>" + line;

        return html;
    }

	private String getPageUrl(String url,int current,int pageSize)
	{
	    if(url.indexOf("/") != -1)
	    {
	        url = url.substring(url.lastIndexOf("/")+1,url.length());
	    }
	    if(url.indexOf("current") == -1)
        {
            if(url.endsWith(".do"))
            {
                url= url + "?" + "current="+current +"&pageSize="+pageSize;
            }
            else
            {
                url= url + "&current="+current +"&pageSize="+pageSize;
            }
        }
        else
        {
            String[] paramArray = url.substring(url.indexOf("?")+1,url.length()).split("&");
            StringBuffer temp = new StringBuffer(url.substring(0,url.indexOf("?")+1));
            for (String string : paramArray)
            {
                if(string.indexOf("current=") != -1)
                {
                    //System.out.println(string.toString());
                    //System.out.println(string.indexOf("current="));
                    string = string.substring(0,string.indexOf("current=")+8)+current;
                    //System.out.println(string);
                }
                if(string.indexOf("pageSize=") != -1)
                {
                    string = string.substring(0,string.indexOf("pageSize=")+9)+pageSize;
                }
                temp.append(string).append("&");
            }
            url = temp.substring(0,temp.length()-1).toString();
            //url = url.substring(0,url.indexOf("current=")+8)+ current + url.substring(url.indexOf("current=")+9,url.length());
            //url = url.substring(0,url.indexOf("pageSize=")+9)+ pageSize + url.substring(url.indexOf("pageSize=")+10,url.length());
        }
	    return url;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}
	
	public String getUrl()
    {
        return url;
    }


    public void setUrl(String url)
    {
        this.url = url;
    }
    
    /**
     * 获取总页数
     */
    public int getRightTotalPage(int rowCount, int pageSize){
        return (rowCount/pageSize) + (rowCount%pageSize == 0 ? 0 : 1);
    }
    
    public void initPageParam(){
        init();
    }
    
    public int getMultiDisplayCount()
    {
        return multiDisplayCount;
    }


    public void setMultiDisplayCount(int multiDisplayCount)
    {
        this.multiDisplayCount = multiDisplayCount;
    }


    @Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		String s = "[first:%d, prev:%d, current:%d, next:%d, last:%d, pageCount:%d, rowCount:%d, start:%d, dataList:%s]";
		return String.format(s, first, prev, current, next, last, pageCount, rowCount, start, dataList);
	}
}