package com.wayhua.framework.core;


import com.wayhua.framework.interf.IDataIterator;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/2/19.
 */
public class PageIterator<V> implements Iterator<Collection<V>>, Iterable<Collection<V>> {

    boolean hasNext = true;
    protected int pageSize = 10;
    protected int nextPage;
    protected XListRequest<V> request;


    public PageIterator(XListRequest<V> request, int nextPage) {
        this.request = request;

        this.nextPage = nextPage;
    }

    public PageIterator(XListRequest<V> request, int nextPage, int pageSize) {
        this(request, nextPage);
        this.pageSize = pageSize;
    }

    @Override
    public Collection<V> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        } else {
            List resources = null;
            IDataIterator<List<V>> client = request.execute(nextPage);
            Object response = client.observable().toBlocking().first();

            if (response != null)
                resources = (List) response;

            hasNext = true;
            if (resources == null) {
                resources = Collections.emptyList();
                hasNext = false;
            } else {
                if (resources.size() < pageSize) {
                    hasNext = false;
                }
            }
            ++this.nextPage;


            return (Collection<V>) resources;

        }
    }


    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove not supported");
    }

    public Iterator<Collection<V>> iterator() {
        return this;
    }

    public interface XListRequest<V> {
        IDataIterator<List<V>> execute(int page);
    }

}
