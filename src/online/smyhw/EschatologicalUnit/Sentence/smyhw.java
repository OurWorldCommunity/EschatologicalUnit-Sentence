package online.smyhw.EschatologicalUnit.Sentence;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class smyhw extends JavaPlugin implements Listener 
{
	public static Logger loger;
	public static FileConfiguration configer;
	public static String prefix;
	public static int delayed;
	@Override
    public void onEnable() 
	{
		getLogger().info("EschatologicalUnit.Sentence加载");
		getLogger().info("正在加载环境...");
		loger=getLogger();
		configer = getConfig();
		getLogger().info("正在加载配置...");
		prefix = configer.getString("config.prefix");
		delayed = configer.getInt("config.delayed");
		saveConfig();
		getLogger().info("正在注册监听器...");
		Bukkit.getPluginManager().registerEvents(this,this);
		getLogger().info("EschatologicalUnit.Sentence加载完成");
    }

	@Override
    public void onDisable() 
	{
		getLogger().info("EschatologicalUnit.Sentence卸载");
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
        if (cmd.getName().equals("euS"))
        {
                if(!sender.hasPermission("eu.plugin")) 
                {
                	sender.sendMessage(prefix+"非法使用 | 使用者信息已记录，此事将被上报");
                	loger.warning(prefix+"使用者<"+sender.getName()+">试图非法使用指令<"+args+">{权限不足}");
                	return true;
                }
                if(args.length<1) 
                {
                	sender.sendMessage(prefix+"非法使用 | 使用者信息已记录，此事将被上报");
                	loger.warning(prefix+"使用者<"+sender.getName()+">试图非法使用指令<"+args+">{参数不足}");
                	return true;
                }
                List<String> temp1 = configer.getStringList("data.Sentence."+args[0]);
                new showThread(temp1);
                return true;                                                       
        }
       return false;
	}
}

class showThread extends Thread
{
	public List<String> msg;
	public showThread(List<String> msg)
	{
		this.msg = msg;
		this.start();
	}
	
	public void run()
	{

        Iterator<String> temp2 =  msg.iterator();
        while(temp2.hasNext())
        {
        	String mmsg = temp2.next();
        	mmsg = mmsg.replace('&', '§');
        	Bukkit.broadcastMessage(mmsg);
        	try {Thread.sleep(smyhw.delayed*1000);} catch (InterruptedException e)
        	{
        		smyhw.loger.warning("消息延迟发送时产生异常！");e.printStackTrace();
			}
        }
	}
}