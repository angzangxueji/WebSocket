import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

public class MyWebSocketServerHandler extends
		SimpleChannelInboundHandler<Object> {
	private static int count=0;
	private static int count2=0;
	private static Set<String>  s = new HashSet();

	private static int color=0; //color为1代表红方，2代表蓝方

    private static ArrayBlockingQueue arrayBlockingQueue=new ArrayBlockingQueue(1000);
    private static Map map=new HashMap();//传一个id
	private static Map mapa=new HashMap();//传a的
    private static Map map1=new HashMap();//传两个id
	private static Map map2=new HashMap();//传消息
	public static HashMap accountmap=new HashMap();//
	private static HashMap hashMap=new HashMap();
	private static HashMap colormap=new HashMap();
	private HandlePy handlePy=new HandlePy();
	private static HashMap h1=new HashMap();
	private static HashMap h2=new HashMap();
	private static int q;
	private static int w;
	private static int e;
	private static int t;
	private static int y;
	private static int u;

	private static int b=0;
	private static final Logger logger = Logger
			.getLogger(WebSocketServerHandshaker.class.getName());

	private WebSocketServerHandshaker handshaker;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		// 添加
		Global.group.add(ctx.channel());
		System.out.println("ctx.id:"+ctx.channel().id().toString());
		s.add(ctx.channel().id().toString());
		count=count+1;

		System.out.println("客户端与服务端连接开启");
		System.out.println("客户端的连接数为  "  +count);

	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

		// 移除
		Global.group.remove(ctx.channel());

		count=count-1;
		System.out.println("客户端与服务端连接关闭");
		System.out.println("客户端的连接数为  "  +count);
		//color=0;//没办法只能放这
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg)
			throws Exception {

		if (msg instanceof FullHttpRequest) {

			handleHttpRequest(ctx, ((FullHttpRequest) msg));

		} else if (msg instanceof WebSocketFrame) {

			handlerWebSocketFrame(ctx, (WebSocketFrame) msg);

		}

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	private void handlerWebSocketFrame(ChannelHandlerContext ctx,
			WebSocketFrame frame) {

		// 判断是否关闭链路的指令
		if (frame instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame
					.retain());
		}

		// 判断是否ping消息
		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(
					new PongWebSocketFrame(frame.content().retain()));
			return;
		}

		// 本例程仅支持文本消息，不支持二进制消息
		if (!(frame instanceof TextWebSocketFrame)) {

			System.out.println("本例程仅支持文本消息，不支持二进制消息");

		}




		StringBuffer sb = new StringBuffer();
		String s1=new String();
		for(String value: s){
			sb.append(value);
			sb.append(" ");
			s1=s1+value+":";
		}
		String s2[]=s1.split(":");

		// 返回应答消息
		String r = ((TextWebSocketFrame) frame).text();
		try {
			arrayBlockingQueue.put(r);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String request= null;
		try {
			request = (String) arrayBlockingQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("==========================================request===============================================");
		System.out.println(request);
		System.out.println("==========================================request===============================================");

		String assemble[]=request.split(";");

		String account[]=assemble[0].split("=");

		if(account[0].equals("a")){
			System.out.println("=========================================同时跳转===========================================");
		//if(request.equals("a")){//前面页面的处理
			int a=2;
		String s=String.valueOf(a);
		if (count==2) {
        //  count=0;
			// 群发
			mapa.put("a",2);
			System.out.println("mapa----------------------------------------------------------------->"+mapa.get(a));
			String jsona= JSON.toJSONString(mapa);
			System.out.println("jsona------------------------------------------------------------------->"+jsona);
			mapa.clear();
			Global.group.writeAndFlush(new TextWebSocketFrame(jsona));
		}
		}


		if(account[0].equals("account0")){
			System.out.println("=========================================同时跳转0===========================================");
			//if(request.equals("a")){//前面页面的处理
			int a=2;
			String s=String.valueOf(a);


			String color0[] = assemble[1].split("=");
		System.out.println("===========================================color0[0]=============================================");
		System.out.println(color0[1]);
		System.out.println("===========================================color0[0]=============================================");

		String code0[] = assemble[2].split("&");
		System.out.println("===========================================code0[0]=============================================");
		System.out.println(code0[1]);
		System.out.println("===========================================code0[0]=============================================");

		// accountmap

		System.out.println("==============================================================================================");

			System.out.println(Integer.parseInt(account[1])+"=================================="+Integer.parseInt(color0[1]));

			handlePy.createPy(code0[1], Integer.parseInt(account[1]));
			handlePy.execPy(accountmap, colormap, Integer.parseInt(account[1]), Integer.parseInt(color0[1]));
			System.out.println("response==========================================================");

			hashMap = (HashMap) accountmap.get(Integer.parseInt("222"));


			if (count==2)   {
				//  count=0;
				// 群发
				System.out.println(" ");
				mapa.put("a",2);
				System.out.println("mapa----------------------------------------------------------------->"+mapa.get(a));
				String jsona= JSON.toJSONString(mapa);
				System.out.println("jsona------------------------------------------------------------------->"+jsona);
				mapa.clear();
				Global.group.writeAndFlush(new TextWebSocketFrame(jsona));


				h1= (HashMap) colormap.get(1);
				System.out.println("h1-----------------------------------------"+h1);
				h2= (HashMap) colormap.get(2);
				System.out.println("h2-----------------------------------------"+h2);
				q= Integer.parseInt(String.valueOf(h1.get("tank_crash_border")));
				w=  Integer.parseInt(String.valueOf(h1.get("tank_crash_border")));
				e=  Integer.parseInt(String.valueOf(h1.get("tank_crash_enemy")));

				u=  Integer.parseInt(String.valueOf(h2.get("tank_crash_border")));
				t=  Integer.parseInt(String.valueOf(h2.get("tank_crash_border")));
				y=  Integer.parseInt(String.valueOf(h2.get("tank_crash_enemy")));
			}

		}


		 if(account[0].equals("channelid")){

				 hashMap = (HashMap) accountmap.get(Integer.parseInt(account[1]));

				 System.out.println("hashmap----------------------------------------------------------->" + hashMap);

				 color = (int) hashMap.get("color");
			     System.out.println("玩家" + color+ "id------------------------------------------------------------>" + account[1]);


				 map.put("color", color);
				 map.put("account",account[1]);
				 map.put("channelid", ctx.channel().id().toString());

				 String json = JSON.toJSONString(map);

				 map.clear();//清空map，等下一个坦克来
				 map1.put("color1", 3);//这个是一起传两个坦克id的map
				 map1.put("_" + color, ctx.channel().id().toString());

				 System.out.println("map1 " + color + "--------------------------------------->" + ctx.channel().id().toString());
				 //String channelid=color+","+ctx.channel().id().toString();
				 ctx.channel().write(new TextWebSocketFrame(json));


				if (count == 2)

				{

					System.out.println("===============================================匹配成功==============================================");
					//count=count-1;
                    //color=0;
                    String json1= JSON.toJSONString(map1);
					System.out.println(map1.get("_"+1));
					System.out.println(map1.get("_"+2));
					System.out.println("---------------------------------当count=2时广播------------------------");
                    Global.group.writeAndFlush((new TextWebSocketFrame(json1)));
//
					System.out.println("count=2--------------------------------------------------------------");
				}

			}  else {

			String[] id = request.split("=");
			String json2;
//			int a;

//			 h1.get("tank_crash_stone");
			if (account[0].equals("tank_path")) {
				System.out.println("id[0] ---------------------------------------->              "+id[0]);

				System.out.println("account[1] ---------------------------------------->              "+account[1]);
			//	a= (int) h1.get("tank_crash_stone");
				//map2.put("master",id[1]);
				map2.put("master",account[1]);
				map2.put("message", "tank_path");
				map2.put("tankpath_choose",q);
				json2 = JSON.toJSONString(map2);
				map2.clear();
				Global.group.writeAndFlush((new TextWebSocketFrame(json2)));
				System.out.println("success ------------------------------------------------------------------");
			}
			else if (account[0].equals("tank_w_path") ){
			//	a= (int) h1.get("tank_crash_border");

				map2.put("master",account[1]);
				map2.put("message", "tank_w_path");
				//map2.put("tankpath_w_choose",  h1.get("tank_crash_border"));
				map2.put("tankpath_w_choose",  w);

				json2 = JSON.toJSONString(map2);
				map2.clear();
				Global.group.writeAndFlush((new TextWebSocketFrame(json2)));

			}
			else if (account[0].equals("tank_meet_enemy_path_choose") ){
				//a= (int) h1.get("tank_crash_enemy");

				map2.put("master",account[1]);
				map2.put("message", "tank_meet_enemy_path_choose");
				map2.put("tank_meet_enemy_path_choose",e);
				json2 = JSON.toJSONString(map2);
				map2.clear();
				Global.group.writeAndFlush((new TextWebSocketFrame(json2)));
			}
			else if (account[0].equals( "enemytank_w_path") ){
			//	a= (int) h2.get("tank_crash_stone");

				map2.put("master",account[1]);
				map2.put("message", "enemytank_w_path");
				map2.put("enemytankpath_w_choose",1);
				json2 = JSON.toJSONString(map2);
				map2.clear();
				Global.group.writeAndFlush((new TextWebSocketFrame(json2)));
			}
			else if (account[0].equals( "enemytank_path") ){
				//	a= (int) h2.get("tank_crash_stone");

				map2.put("master",account[1]);
				map2.put("message", "enemytank_path");
				map2.put("enemytankpath_choose",t);
				json2 = JSON.toJSONString(map2);
				map2.clear();
				Global.group.writeAndFlush((new TextWebSocketFrame(json2)));
			}
			else if (account[0] .equals( "enemy_meet_tank_path_choose")) {
			//	a= (int) h2.get("tank_crash_stone");

				map2.put("master",account[1]);
				map2.put("message", "enemy_meet_tank_path");
				map2.put("enemy_meet_tank_path_choose", y);
				json2 = JSON.toJSONString(map2);
				map2.clear();
				Global.group.writeAndFlush((new TextWebSocketFrame(json2)));
			}

		}


	}

	private void handleHttpRequest(ChannelHandlerContext ctx,
			FullHttpRequest req) {

		if (!req.decoderResult().isSuccess()
				|| (!"websocket".equals(req.headers().get("Upgrade")))) {

			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(
					HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));

			return;
		}

		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				"ws://0.0.0.0:7397/websocket", null, false);

		handshaker = wsFactory.newHandshaker(req);

		if (handshaker == null) {
			WebSocketServerHandshakerFactory
					.sendUnsupportedVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}

	}

	private static void sendHttpResponse(ChannelHandlerContext ctx,
			FullHttpRequest req, DefaultFullHttpResponse res) {

		// 返回应答给客户端
		if (res.status().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(),
					CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
		}

		// 如果是非Keep-Alive，关闭连接
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!isKeepAlive(req) || res.status().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	private static boolean isKeepAlive(FullHttpRequest req) {

		return false;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {

		cause.printStackTrace();
		ctx.close();

	}

}
