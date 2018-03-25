package com.cn.server.module.player.service;

import com.cn.common.core.session.Session;
import com.cn.common.module.player.proto.PlayerModule.PlayerResponse;
/**
 * 玩家服务
 * @author -琴兽-
 *
 */
public interface PlayerService {
	
	
	/**
	 * 登录注册用户
	 * @param playerName
	 * @param passward
	 * @return
	 */
	public PlayerResponse registerAndLogin(Session session, String playerName, String passward);
	
	
	/**
	 * 登录
	 * @param playerName
	 * @param passward
	 * @return
	 */
	public PlayerResponse login(Session session, String playerName, String passward);

}
