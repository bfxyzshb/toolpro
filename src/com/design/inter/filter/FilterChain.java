package com.design.inter.filter;

import java.util.ArrayList;
import java.util.List;


public class FilterChain implements Filter{
    //存储链
    private List<Filter> filterList=new ArrayList<>();
    private int index=0;

    FilterChain(List<Filter> filterList){
      this.filterList=filterList;
    }

    public void addFilter(Filter filter){
       this.filterList.add(filter);
    }
    @Override
    public Response doFilter(FilterChain filterChain){
        Response response=null;
        Filter filter=filterList.get(index);
        response=filter.doFilter(filterChain);
        index++;
        return response;
    }
}
