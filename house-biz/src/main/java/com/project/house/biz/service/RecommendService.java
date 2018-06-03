package com.project.house.biz.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.project.house.common.model.House;
import com.project.house.common.page.PageData;
import com.project.house.common.page.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendService {

    @Value("${spring.redis.host}")
    private String server;

    @Value("${spring.redis.password}")
    private String password;

    @Autowired
    private HouseService houseService;

    private static final String HOT_HOUSE_KEY = "hot_house";

    public void increase(Long id) {
        Jedis jedis = new Jedis(server);
        jedis.auth(password);
        jedis.zincrby(HOT_HOUSE_KEY, 1.0D, id + "");
        jedis.zremrangeByRank(HOT_HOUSE_KEY, 10, -1);
        jedis.close();
    }

    public List<Long> getHot() {
        Jedis jedis = new Jedis(server);
        jedis.auth(password);
        Set<String> idSet = jedis.zrevrange(HOT_HOUSE_KEY, 0, -1);
        List<Long> ids = idSet.stream().map(Long::parseLong).collect(Collectors.toList());
        return ids;
    }

    public List<House> getHotHouse(Integer size) {
        House query = new House();
        List<Long> list = getHot();
        list = list.subList(0, Math.min(size, list.size()));
        if (list.isEmpty()) {
            return Lists.newArrayList();
        }
        query.setIds(list);
        List<House> houseList = houseService.queryHouse(query, PageParams.bulid(1, size)).getList();
        final List<Long> order = list;
        Ordering<House> houseSort=Ordering.natural().onResultOf(hs-> {
             return order.indexOf(hs.getId());
        });
        return houseSort.sortedCopy(houseList);
    }

    public List<House> getLastest() {
        House query = new House();
        query.setSort("time_desc");
        return houseService.queryHouse(query, PageParams.bulid(1, 8)).getList();

    }
}
