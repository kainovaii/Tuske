package lu.kainovaii.tuske;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleConfig{

	private JavaPlugin pl;
	private boolean hasChanged = false;
	private HashMap<String, String> map = new HashMap<>();
	public SimpleConfig(JavaPlugin plugin){
		pl = plugin;
	}
	public void loadDefault(){
		setDefault("use_metrics", true,
			"#Use metrics to send anonymous data about your server. The data that",
			"#is sent are:",
			"#",
			"#Players currently online (not max player count)",
			"#Version of the server (the same version you see in /version)",
			"#Version of this plugin",
			"#",
			"#If you don't agree with this, you can set it to false freely.",
			"#These values will be used only for statistic for this plugin.");
		setDefault("updater.check_for_new_update", true,
			"#It will check for new update everytime the server starts or",
			"#when someone use the command /tuske update check");
		setDefault("updater.auto_update", false,
			"#It will auto update the plugin. When there is a new version,",
			"#the plugin will download it and update when the server restarts.",
			"#Warning: I can't guarantee that the plugin is free of bugs that",
			"#can come in newest updates. I don't recommend to use in your main",
			"#server.",
			"#You can still download/update your plugin by command, see more in",
			"#/tuske update");
		String version = pl.getDescription().getVersion();
		setDefault("updater.download_pre_releases", (version.contains("beta") || version.contains("dev")),
			"#Download pre-releases.",
			"#Note: pre-releases versions shoudln't be used in your main server.",
			"#It's just to test new incomming features only!!");
		addCommentsAbove("disable",
			"#This option will be for future things of TuSKe.",
			"#It will be used when there is some features that isn't available",
			"#or uncompatible with your version. For now, it doesn't do nothing",
			"#but it tends to disable some crashing expression or lagging event",
			"#",
			"#true if you want to disable. (not implemented yet)");
		setDefault("disable.SomeExample", true);
		setDefault("debug_mode", false, 
			"#This option will be for future things of TuSKe.",
			"#It will just show some debug messages if needed.",
			"#So far, it doesn't do nothing, only prevent some testing debug messages",
			"#that can be accidentaly forgot inside the code.");
		setDefault("warn_unsafe_expressions", true,
			"#It will be used to warn about expressions that may have risk to your server,",
			"#The warn is sent when reloading a script and it won't prevent the expression to work.",
			"#An example is the expression 'random strings matching %regex%'.");
		setDefault("use_only_enum_names", false, 
			"#This is only needed in case of conflict with Skript or another addon",
			"#It will make some types, that is registered by TuSKe and if the type is a Enum,",
			"#to accept the form as '<Enum type>.<Enum name>'",
			"#For example, TuSKe register the type 'InventoryType' for the expression to create inventories",
			"#So, in case the value 'chest' is conflicting with something else, just enable it and",
			"#it will only accept if used like 'InventoryType.CHEST'",
			"#Example:",
			"#\topen virtual InventoryType.CHEST inventory with size 1 named \"Hi\" to player",
			"#Don't need to worry about it, is just in case.");
		addCommentsAbove("documentation",
			"#A documentation that will be generated at 'plugins/TuSKe/documentation/'",
			"#for all addons");
		setDefault("documentation.enabled", true,
			"#Should documentation be generated?");
		setDefault("documentation.file_type", "default",
			"#What type of file the documentation should be generated?",
			"#By default, it uses a format to be easier to see which ends with '.sk'.",
				"#In case you want to use this documentation externally, there are few options:",
				"#  json: Generates a file with json format.",
				"#  raw json: Generates a file with raw json format (single line only).",
				"#  yaml: Generates a file in yaml format.",
				"#  markdown: Generates in Markdown format, useful to github's wiki.",
				"#  default: The default format described above.");
		//Might remove it. It's kind of waste of time trying to make it safe.
		/*addCommentsAbove("evaluate_filter",
			"#Filter some effects/conditions/expressions from being used in evaluate effects.",
			"#First, go to '/TuSKe/documentation' and get every syntax you don't want to be used in",
			"#eval effect. Add them in list below and reload the config with '/tuske reload config'.",
			"#Notes:",
			"#  - You need to include 'with safety' in your evaluate effect. i.e.",
			"#    \tevaluate with safety: stop server #It will actually check if 'stop server' is allowed or not",
			"#  - If you have SkQuery, you might need to disallow its evaluate effect as well.",
			"#  - The performance may be decreased depending of amount of syntaxes. Use it only for public reasons.");
		setDefault("evaluate_filter.mode", "blacklist",
			"#Mode to filter syntaxes: whitelist or blacklist");
		setDefault("evaluate_filter.syntaxes",
			new String[]{"op %player%", "stop server"},
			"#A list of syntaxes to add to whitelist/blacklist.",
			"#Use quotes to properly use the yaml file, example:",
			"#- \"kill %player%\"");*/
		
		//replace the config with the old values.
		String str = "use-metrics";
		if (pl.getConfig().isBoolean(str)){
			pl.getConfig().set(str.replaceAll("\\-", "_"), pl.getConfig().getBoolean(str));
			pl.getConfig().set(str, null);
		}
		for (String var : new String[]{"check-for-new-update", "auto-update"}){
			if (pl.getConfig().isBoolean(var)){
				pl.getConfig().set("updater." + var.replaceAll("-", "_"), pl.getConfig().getBoolean(var));
				pl.getConfig().set(var, null);
			}
			
		}
	}
	private void setDefault(String path, Object value, String... comments){
		if (comments.length > 0)
			addCommentsAbove(path, comments);
		Object obj = pl.getConfig().get(path);
		if (obj == null || (!obj.getClass().equals(value.getClass()) && !value.getClass().isArray())) {
			hasChanged = true;
			pl.getConfig().set(path, value);
		}
		
	
	}
	private void addCommentsAbove(String path, String... comments){
		if (!map.containsKey(path))
			map.put(path, (map.size() > 0 ? "\n" : "")+ StringUtils.join(comments, "\n"));
	}
	public void save(File file){
		if (!hasChanged) //No need to save if the config is updated.
			return;
		try {
			String str = saveToString();
			if (str == null)
				return;
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(str);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private String saveToString(){
		if (map.size() == 0)
			return null;
		String toFile = pl.getConfig().saveToString();
		for (Map.Entry<String, String> entry : map.entrySet()){
			String key = entry.getKey();
			String comment = entry.getValue();
			int last = key.split("\\.").length -1;
			StringBuilder space = new StringBuilder("");
			for (int x = 0; x < last; x++)
				space.append("  ");
			comment = comment.replaceAll("\n", "\n" + space) + "\n";
			String regex = keyToRegex(key);
			if (!key.equalsIgnoreCase(regex))
				toFile = toFile.replaceFirst("(?s)"+ Matcher.quoteReplacement(regex), "$1$2" +  comment + space + "$3");
			else
				toFile = toFile.replaceFirst(Matcher.quoteReplacement(key), comment + key);
		}
		map.clear();
		return toFile;
	}
	private String keyToRegex(String key){		
		return key.replaceAll("^((\\w+(\\s+|-)?)+)(\\.(.+\\.)?)((\\w+(\\s+|-)?)+)$", "($1:)(.+)($6:)"); //TODO fix that regex pattern
	}
}
