package cn.rrg.rdv.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import cn.rrg.rdv.settings.BaseSetting;
import cn.rrg.rdv.settings.ChameleonSlotAliasesSetting;
import cn.rrg.rdv.settings.ChameleonSlotAliasesStatusSetting;
import cn.rrg.rdv.settings.CommonRWKeyFilesSelectedSettings;
import cn.rrg.rdv.settings.LanguageSettings;
import cn.rrg.rdv.settings.MainPageColumnCountSetting;
import cn.rrg.rdv.settings.Proxmark3ConsleDelaySetting;
import cn.dxl.common.util.AssetsUtil;
import cn.dxl.common.util.DiskKVUtil;

public class InitUtil {

    public static final String LOG_TAG = InitUtil.class.getSimpleName();

    public static void initApplicationResource(Context context) {
        AssetsUtil au = new AssetsUtil(context);
        File dump_dir = new File(Paths.DUMP_DIRECTORY);
        if (!dump_dir.exists()) {
            if (!dump_dir.mkdirs()) {
                Log.d(LOG_TAG, "init dumps dir fail!");
            } else {
                initDumpFile(au);
            }
        } else {
            initDumpFile(au);
        }
        File driver_dir = new File(Paths.DRIVER_DIRECTORY);
        if (!driver_dir.exists()) {
            if (!driver_dir.mkdirs()) {
                Log.d(LOG_TAG, "init driver dir fail!");
            }
        }
        File keys_dir = new File(Paths.KEY_DIRECTORY);
        if (!keys_dir.exists()) {
            if (!keys_dir.mkdirs()) {
                Log.d(LOG_TAG, "init keys dir fail!");
            } else {
                initKeysFile(au);
            }
        } else {
            initKeysFile(au);
        }
        File common_dir = new File(Paths.COMMON_DIRECTORY);
        if (!common_dir.exists()) {
            if (common_dir.mkdirs()) {
                initCommonInFile();
                initMfInfoMapsFile(au);
            }
        } else {
            initCommonInFile();
            initMfInfoMapsFile(au);
        }
        File pn53x_dir = new File(Paths.PN53X_DIRRECTORY);
        if (!pn53x_dir.exists()) {
            if (!pn53x_dir.mkdirs()) {
            } else {
                initPN53XForwardFile();
            }
        } else {
            initPN53XForwardFile();
        }
        File pm3_dir = new File(Paths.PM3_DIRECTORY);
        if (!pm3_dir.exists()) {
            if (!pm3_dir.mkdirs()) {
                Log.d(LOG_TAG, "init pm3 dir fail!");
            } else {
                initProxmark3RDV4ImageFile(au);
                initEasyButtonFile(au);
                initPM3ForwardFile();
                initHardnestedFile(au);
            }
        } else {
            initProxmark3RDV4ImageFile(au);
            initEasyButtonFile(au);
            initPM3ForwardFile();
            initHardnestedFile(au);
        }
    }

    private static void initCommonInFile() {
        //初始化标准输入的文件!
        File stdInFile = new File(Paths.COMMON_FORWARD_I);
        if (!stdInFile.exists()) {
            try {
                if (!stdInFile.createNewFile()) {
                    Log.d(LOG_TAG, "创建标准输入文件失败!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initProxmark3RDV4ImageFile(AssetsUtil au) {
        //初始化标准输入的文件!
        File boot = new File(Paths.PM3_IMAGE_BOOT_FILE);
        File os = new File(Paths.PM3_IMAGE_OS_FILE);
        au.moveFile(Paths.PM3_BOOT_FILE_NAME, boot.getAbsolutePath());
        au.moveFile(Paths.PM3_OS_FILE_NAME, os.getAbsolutePath());
    }

    private static void initMfInfoMapsFile(AssetsUtil au) {
        File f1 = new File(Paths.COMMON_DIRECTORY + "/" + "template_mifare_info_en.html");
        File f2 = new File(Paths.COMMON_DIRECTORY + "/" + "template_tag_info_en.html");

        //移动两个标签信息HTML文件到指定目录!
        au.moveFile("template_mifare_info_en.html",
                new File(Paths.COMMON_DIRECTORY + "/" + "template_mifare_info_en.html").getAbsolutePath());
        au.moveFile("template_tag_info_en.html",
                new File(Paths.COMMON_DIRECTORY + "/" + "template_tag_info_en.html").getAbsolutePath());
    }

    private static void initDumpFile(AssetsUtil au) {
        File target = new File(Paths.DEFAULT_DUMP_FILE);
        //目标文件夹里是否有这个文件!
        if (!(target.exists() && target.isFile())) {
            //包里面是否有这个资源文件!
            if (au.isFileExists(Paths.DEFAULT_DUMP_NAME)) {
                if (au.moveFile(Paths.DEFAULT_DUMP_NAME, Paths.DEFAULT_DUMP_FILE)) {
                    Log.d(LOG_TAG, "创建默认的空白数据文件成功!");
                }
            }
        }
    }

    private static void initKeysFile(AssetsUtil au) {
        File target = new File(Paths.DEFAULT_KEYS_FILE);
        //目标文件夹里是否有这个文件!
        if (!(target.exists() && target.isFile())) {
            //包里面是否有这个资源文件!
            if (au.isFileExists(Paths.DEFAULT_KEYS_NAME)) {
                if (au.moveFile(Paths.DEFAULT_KEYS_NAME, Paths.DEFAULT_KEYS_FILE)) {
                    Log.d(LOG_TAG, "创建默认的空白数据文件成功!");
                }
            }
        }
    }

    private static void initEasyButtonFile(AssetsUtil au) {
        File target = new File(Paths.PM3_CMD_FILE);
        //目标文件夹里是否有这个文件!
        if (!(target.exists() && target.isFile())) {
            //包里面是否有这个资源文件!
            if (au.isFileExists(Paths.DEFAULT_CMD_NAME)) {
                if (au.moveFile(Paths.DEFAULT_CMD_NAME, Paths.PM3_CMD_FILE)) {
                    Log.d(LOG_TAG, "创建默认的动态按钮文件成功!");
                }
            }
        }
    }

    private static void initPM3ForwardFile() {
        //尝试初始化pm3的相关文件!
        File pm3_forward_o = new File(Paths.PM3_FORWARD_O);
        File pm3_forward_e = new File(Paths.PM3_FORWARD_E);
        try {
            pm3_forward_o.createNewFile();
            pm3_forward_e.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initHardnestedFile(AssetsUtil au) {
        File pm3_hardnested_dir = new File(Paths.PM3_HARDNESTED_DIRECTORY);
        if (!pm3_hardnested_dir.exists()) {
            if (!pm3_hardnested_dir.mkdirs()) {
            } else {
                initHardnestedBinFile(au);
            }
        } else {
            initHardnestedBinFile(au);
        }
        File pm3_hardnested_tables_dir = new File(Paths.PM3_HARDNESTED_TABLES_DIRECTORY);
        if (!pm3_hardnested_tables_dir.exists()) {
            if (pm3_hardnested_tables_dir.mkdirs()) {
                initHardnestedNoncesFile(au);
            }
        } else {
            initHardnestedNoncesFile(au);
        }
    }

    private static void initHardnestedBinFile(AssetsUtil au) {
        File targetFile = new File(Paths.PM3_HARDNESTED_DIRECTORY + "/" + "bf_bench_data.bin");
        if (targetFile.exists()) {
            return;
        }
        //移动bench文件!
        au.moveFile(
                Paths.HARDNESTED_PATH + "/" + "bf_bench_data.bin", //要移动的文件!
                targetFile.getAbsolutePath()  //目标目录与文件名!
        );
    }

    private static void initHardnestedNoncesFile(AssetsUtil au) {
        //pm3的随机数种子文件，判断文件数量，如果小于0，就进行拷贝!
        File tmpFile = new File(Paths.PM3_HARDNESTED_TABLES_DIRECTORY);
        int size = tmpFile.list().length;
        if (size > 0) {
            return;
        }
        String tmpTablesPath = Paths.HARDNESTED_PATH + "/" + Paths.TABLES_PATH;
        String[] files = au.getFiles(tmpTablesPath);
        if (files != null) {
            //进行最终的移动!
            for (String f : files) {
                au.moveFile(
                        tmpTablesPath + "/" + f,
                        Paths.PM3_HARDNESTED_TABLES_DIRECTORY + "/" + f
                );
            }
        }
    }

    private static void initPN53XForwardFile() {
        File o_file = new File(Paths.PN53X_FORWARD_O);
        File e_file = new File(Paths.PN53X_FORWARD_E);
        File o_mf_file = new File(Paths.PN53X_FORWARD_MF_O);
        File e_mf_file = new File(Paths.PN53X_FORWARD_MF_E);
        try {
            o_file.createNewFile();
            e_file.createNewFile();
            o_mf_file.createNewFile();
            e_mf_file.createNewFile();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void startLogcat(boolean open) {
        //拼接名字
        String name = "log.txt";
        //检查或者常创建日志目录和文件
        File log_dir = new File(Paths.LOG_DIRECTORY);
        if (!(log_dir.exists() && log_dir.isDirectory())) {
            if (!log_dir.mkdirs()) {
                Log.d(LOG_TAG, "log dir create fail!");
                return;
            }
        }
        File log_file = new File(log_dir.getPath() + "/" + name);
        //判断旧文件是否存在，有则删除
        if (log_file.exists() && log_file.isFile()) log_file.delete();
        try {
            if (open) {
                //清除旧的日志
                Runtime.getRuntime().exec("logcat -c");
                //开始录入日志
                Runtime.getRuntime().exec("logcat -f " + log_file.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initSettings() throws IOException {
        File dir = new File(Paths.SETTINGS_DIRECTORY);
        if (dir.exists()) {
            initSets();
        } else {
            if (dir.mkdirs()) {
                initSets();
            }
        }
    }

    private static void initSets() throws IOException {
        File file = new File(Paths.SETTINGS_FILE);
        if (!file.exists())
            if (!file.createNewFile()) return;
        BaseSetting[] baseSettings = new BaseSetting[]{
                new ChameleonSlotAliasesSetting(),
                new ChameleonSlotAliasesStatusSetting(),
                new MainPageColumnCountSetting(),
                new Proxmark3ConsleDelaySetting(),
                new LanguageSettings(),
                new CommonRWKeyFilesSelectedSettings()
        };
        //迭代设置配置实现类，进行设置参数的初始化!
        for (BaseSetting setting : baseSettings) {
            //先获得配置文件!
            File setFileTmp = setting.getSettingsFile();
            Log.d(LOG_TAG, "The settings config file is: " + setFileTmp.getName());
            String key = setting.onQuerySetting();
            //然后检查设置是否存在!
            if (DiskKVUtil.isKVExists(key, setFileTmp)) {
                //存在，则取出值!
                setting.onNormal(DiskKVUtil.queryKVLine(key, setFileTmp));
            } else {
                //不存在，我们需要进行通知!
                setting.onNotFound(setting.onQuerySetting());
            }
        }
    }
}