package RgOwnerUni;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.protection.regions.*;

import java.util.LinkedList;
import java.util.Collection;
import java.util.UUID;


public class rgowner extends JavaPlugin{
	private Plugin wgPlugin;

	@Override
	public void onLoad(){
		wgPlugin = Bukkit.getPluginManager().getPlugin("WorldGuard");
		Bukkit.getLogger().info("WorldGuard found.");
	}

	@Override
	public void onEnable(){
		// Do nothing
	}

	@Override
	public void onDisable(){
		// Do nothing
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args){
		if((sender instanceof Player)){
			RegionManager rm = ((WorldGuardPlugin)wgPlugin).getRegionManager(((Player)sender).getWorld());

			Collection<ProtectedRegion> regionIterator = rm.getRegions().values();
			LinkedList<ProtectedRegion> matches = new LinkedList<ProtectedRegion>();
			for(ProtectedRegion pr : regionIterator){
				UUID uniqueId = ((Player)sender).getUniqueId();
				DefaultDomain dd = pr.getOwners();
				if(dd.contains(uniqueId)) matches.add(pr);
			}
			
			LinkedList<ProtectedRegion> list = new LinkedList<ProtectedRegion>();
			for(ProtectedRegion pr : matches){
				list.add(pr);
			}
	
			StringBuilder sb = new StringBuilder();
			sb.append(ChatColor.DARK_AQUA + "You are owner of the following regions:\n");
			
			ProtectedRegion[] matchArray = new ProtectedRegion[list.size()];
			int matchArrayIndex = 0;
			for(ProtectedRegion pr : list){
				matchArray[matchArrayIndex++] = pr;
			}
			for(int i = 0; i < list.size(); i++){
				sb.append(matchArray[i].getId());
				if(i != matchArray.length - 1){
					sb.append(", ");
				}
			}
			if(list.isEmpty()) sb.append("None");
			sender.sendMessage(sb.toString());

		}
		else{
			sender.sendMessage(ChatColor.RED + "You cannot run this command as console! Thanks Morton");
		}
		
		return true;
	}
}
