package com.ocean.lmh.base.wrapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ParameterRequestWrapper extends HttpServletRequestWrapper
{

    private Map<String, String[]> params = new HashMap<String, String[]>();

    @SuppressWarnings("unchecked")
	public ParameterRequestWrapper(HttpServletRequest request)
    {
        // 将request交给父类，以便于调用对应方法的时候，将其输出，其实父亲类的实现方式和第一种new的方式类似
        super(request);
        
        // 将参数表，赋予给当前的Map以便于持有request中的参数
        this.params.putAll(request.getParameterMap());
        this.modifyParameterValues();
    }

    public void modifyParameterValues()
    {
        // 将parameter的值去除空格后重写回去
        Set<String> set = params.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext())
        {
            String key = (String) it.next();
            String[] values = params.get(key);
            values[0] = values[0].trim();
            params.put(key, values);
        }
    }
}
