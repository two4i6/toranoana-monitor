package com.luobo.toranoana_monitor.dao;

import com.luobo.toranoana_monitor.framework.Dao;
import com.luobo.toranoana_monitor.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UrlDataDao implements Dao<UrlData> {
    private static final UrlDataDao urlDataDao = new UrlDataDao();
    private List<UrlData> urlDataList = new ArrayList<>();
    int index = 0;

    @Override
    public Optional<UrlData> add(int id) {
        Util util = Util.getUtil();
        String catLoading = "https://luobo.ca/img/loading.gif";
        UrlData urlData = new UrlData(index, id, util.getTime(), util.getUrl(id), catLoading);
        urlData.setUrlStr(urlData.getUrlStr());
        index++;
        urlDataList.add(urlData);
        return Optional.of(urlData);
    }

    @Override
    public Optional<UrlData> get(int index) {
        return Optional.ofNullable(urlDataList.get(index));
    }

    @Override
    public Collection<UrlData> getAll() {
        return urlDataList;
    }

    public Collection<UrlData> getAllValid() {
        return   UrlDataDao.getUrlDataDao().getAll().stream()
                .filter(UrlData::isValid)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Collection<UrlData> getAllInvalid() {
        return   UrlDataDao.getUrlDataDao().getAll().stream()
                .filter(s -> !s.isValid())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Collection<UrlData> getAllFiltered() {
        return   UrlDataDao.getUrlDataDao().getAll().stream()
                .filter(s -> !s.isPassword())
                .filter(UrlData::isValid)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Collection<UrlData> getAllFilteredValid() {
        return   UrlDataDao.getUrlDataDao().getAll().stream()
                .filter(s -> s.isPassword() || s.isKey())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void update(UrlData urlData) {
        urlDataList.set(urlData.getIndex(),urlData);
    }

    @Override
    public void delete(UrlData urlData) {
        urlDataList.set(urlData.getIndex(),null);
    }

    @Override
    public int size() {
        return urlDataList.size();
    }

    @Override
    public boolean allValid(){
        return getAllInvalid().isEmpty();
    }

    @Override
    public boolean isEmpty(){
        return size() == 0;
    }

    public void drop() {
        urlDataList = new ArrayList<>();
    }

    public static UrlDataDao getUrlDataDao(){
        return urlDataDao;
    }
}
