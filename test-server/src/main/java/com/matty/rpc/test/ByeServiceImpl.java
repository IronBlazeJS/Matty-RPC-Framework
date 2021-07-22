package com.matty.rpc.test;

import com.matty.rpc.annotation.Service;
import com.matty.rpc.api.ByeService;

/**
 * ClassName: ByeServiceImpl
 * author: Matty Roslak
 * date: 2021/7/22  11:45
 */
@Service
public class ByeServiceImpl implements ByeService {

    @Override
    public String bye(String name) {
        return "bye," + name;
    }
}
