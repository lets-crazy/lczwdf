package com.lcz.wdf.utils;

import com.lcz.wdf.entity.exception.BizException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 文件操作类
 *
 * @author lin.xuancheng
 * @version 1.0
 * @since 2021/1/12 17:26
 */
public class FileUtil {

    private FileUtil() {
    }

    /**
     * 计算文件大小
     *
     * @param byteCount 字节数
     * @return java.lang.String 文件大小 B,KB,MB,GB
     * @author lin.xuancheng
     * @date 2020/12/11 18:43
     */
    public static String computeFileSize(long byteCount) {
        // 字节换算数
        final double alternateUnit = 1024D;
        final String b = "B";
        final String kb = "KB";
        final String mb = "MB";
        final String gb = "GB";
        final String tb = "TB";
        final String format = "%.2f";
        double size;

        if (byteCount < alternateUnit) {
            return String.valueOf(byteCount).concat(b);
        } else {
            size = byteCount / alternateUnit;
        }

        if (size < alternateUnit) {
            return String.format(format, size).concat(kb);
        } else {
            size = size / alternateUnit;
        }

        if (size < alternateUnit) {
            return String.format(format, size).concat(mb);
        } else {
            size = size / alternateUnit;
        }

        if (size < alternateUnit) {
            return String.format(format, size).concat(gb);
        } else {
            size = size / alternateUnit;
        }

        if (size < alternateUnit) {
            return String.format(format, size).concat(tb);
        } else {
            throw new UnsupportedOperationException("文件大小超过TB!");
        }
    }

    /**
     * 解压文件夹
     *
     * @param filePath 待解压文件路径 (*.zip)
     * @param dirPath  解压后文件夹路径
     * @author lin.xuancheng
     * @since 2020/12/24 17:50
     */
    public static void decompress(Path filePath, Path dirPath) throws BizException {
        checkParamsForDecompress(filePath, dirPath);
        // 解压文件
        try (ZipArchiveInputStream zipArchiveInputStream =
                     new ZipArchiveInputStream(
                             Files.newInputStream(filePath))) {
            ZipArchiveEntry entry;
            int index = 0;
            int pathFilterLength = 0;
            while ((entry = zipArchiveInputStream.getNextZipEntry()) != null) {
                if (!zipArchiveInputStream.canReadEntryData(entry)) {
                    continue;
                }
                Path entryPath;
                entryPath = Paths.get(dirPath.toString(),
                        entry.getName().substring(pathFilterLength));
                if (entry.isDirectory()) {
                    if (index > 0) {
                        Files.createDirectory(entryPath);
                        Files.setLastModifiedTime(entryPath, FileTime.fromMillis(entry.getTime()));
                    } else {
                        pathFilterLength = entry.getName().substring(1, entry.getName().length() - 1).length() + 1;
                    }
                } else {
                    Files.copy(zipArchiveInputStream, entryPath);
                }
                if (index > 0 && Files.notExists(entryPath)) {
                    throw new BizException(
                            String.format("decompress error ! path = %s ", entryPath));
                }
                index++;
            }
        } catch (IOException e) {
            throw new BizException("decompress fail! ", e);
        }
    }

    private static void checkParamsForDecompress(Path filePath, Path dirPath) throws BizException {
        // 判断文件
        String zipFlag = ".zip";
        if (Files.notExists(filePath)
                || Files.isDirectory(filePath)
                || !filePath.toString().endsWith(zipFlag)) {
            throw new BizException("decompress fail ! file is error !");
        }

        // 判断文件夹
        if (Files.notExists(dirPath) || !Files.isDirectory(dirPath)) {
            throw new BizException("decompress fail ! dir not exist!");
        }
    }

    /**
     * 压缩文件夹 (*.zip)
     *
     * @param dirPath    文件夹路径
     * @param targetPath 压缩文件路径
     * @author lin.xuancheng
     * @since 2020/12/24 17:50
     */
    public static void compress(Path dirPath, Path targetPath, int maxDepth) throws BizException {
        // 检查参数
        checkParamsForCompress(dirPath, targetPath);

        try (ZipArchiveOutputStream out =
                     new ZipArchiveOutputStream(
                             new BufferedOutputStream(new FileOutputStream(targetPath.toFile())))) {
            try (final Stream<Path> walk =
                         Files.walk(dirPath, maxDepth, FileVisitOption.FOLLOW_LINKS)) {
                for (Path path : walk.collect(Collectors.toList())) {
                    File file = path.toFile();
                    String entryPath = removeRootPath(dirPath, path);
                    ZipArchiveEntry entry =
                            (ZipArchiveEntry) out.createArchiveEntry(file, entryPath);
                    entry.setTime(file.lastModified());
                    out.putArchiveEntry(entry);
                    if (!Files.isDirectory(path)) {
                        Files.copy(path, out);
                    }
                    out.closeArchiveEntry();
                }
            }
        } catch (IOException e) {
            throw new BizException("compress fail!", e);
        }
    }

    /**
     * 移除根目录路径作为压缩包内路径
     *
     * @param dirPath 根路径
     * @param path    文件路径
     * @return 压缩包内的路径
     * @author lin.xuancheng
     * @since 2020/12/28 21:41
     */
    private static String removeRootPath(Path dirPath, Path path) {
        String newPath = path.toString().substring(dirPath.getParent().toString().length());
        if (newPath.startsWith(File.separator)) {
            newPath = newPath.substring(1);
        }
        return newPath;
    }

    /**
     * 检查参数
     *
     * @param dirPath 文件夹路径
     * @throws BizException 业务异常
     * @author lin.xuancheng
     * @since 2021/1/12 17:35
     */
    private static void checkParamsForCompress(Path dirPath, Path destPath) throws BizException {
        // 判断目标文件
        if (Files.exists(destPath) && !Files.isDirectory(destPath)) {
            throw new BizException("decompress fail ! file is error !");
        }

        // 判断文件夹
        if (Files.notExists(dirPath) || !Files.isDirectory(dirPath)) {
            throw new BizException("decompress fail ! dir is error !");
        }
    }

    /**
     * 遍历文件夹并获取所有文件名
     *
     * @param dirPath 文件夹路径
     * @return 文件夹名列表
     * @throws IOException 读取文件异常
     * @author lin.xuancheng
     * @since 2021/1/6 22:08
     */
    public static List<String> getAllFileNamesOfDir(Path dirPath, int maxDepth) throws IOException {
        if (Files.exists(dirPath, LinkOption.NOFOLLOW_LINKS)
                && Files.isDirectory(dirPath, LinkOption.NOFOLLOW_LINKS)) {
            try (final Stream<Path> walk =
                         Files.walk(dirPath, maxDepth, FileVisitOption.FOLLOW_LINKS)) {
                return walk.map(path -> path.getFileName().toString()).collect(Collectors.toList());
            }
        }
        throw new UnsupportedOperationException("only support dir!");
    }

    /**
     * 写入文件内容
     *
     * @param content         待写入的文件内容
     * @param filePath        本地文件路径，相对路径或者绝对路径
     * @param isAppend        是否在原有的文件追加
     * @param isBackupOldFile 是否备份原有的文件
     * @author linwei
     * @since 2020/11/26 2:14 下午
     */
    public static void writeStringToFile(String filePath, String content, boolean isAppend, boolean isBackupOldFile) throws IOException {
        File outputFile = Path.of(filePath).toFile();
        if (outputFile.exists() && isBackupOldFile) {
            String bakFilePath =
                    filePath
                            + ".bak."
                            + System.currentTimeMillis()
                            + "."
                            + UUID.randomUUID().toString();
            org.apache.commons.io.FileUtils.copyFile(new File(filePath), new File(bakFilePath));
        }
        org.apache.commons.io.FileUtils.writeStringToFile(
                new File(filePath), content, UTF_8, isAppend);
    }

    /**
     * 从文件中读取
     *
     * @return 读取的字符串
     * @throws BizException 业务异常
     * @author lin.xuancheng
     * @since 2020/12/23 15:03
     */
    public static String readStringFromFile(Path filePath) throws BizException {
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            return reader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 移除文件
     *
     * @throws IOException io 异常
     * @author lin.xuancheng
     * @since 2020/12/23 15:03
     */
    public static void removeFile(Path filePath) throws IOException, BizException {
        // 判空
        if (Objects.isNull(filePath)) {
            throw new BizException("error! file path is null !");
        }

        // 判断是否存在
        if (Files.exists(filePath, LinkOption.NOFOLLOW_LINKS)) {
            // 正常情况为存在
            Files.deleteIfExists(filePath);
        }
    }

    /**
     * 移除文件夹
     *
     * @param dirPath 文件夹路径
     * @throws IOException  I/O异常
     * @throws BizException 业务异常
     * @author lin.xuancheng
     * @since 2020/12/23 15:03
     */
    public static void removeDir(Path dirPath) throws IOException, BizException {
        // 判空
        if (Objects.isNull(dirPath)) {
            throw new BizException("error! directory path is null !");
        }
        // 判断是否存在
        if (Files.exists(dirPath, LinkOption.NOFOLLOW_LINKS) && Files.isDirectory(dirPath, LinkOption.NOFOLLOW_LINKS)) {
            Files.deleteIfExists(dirPath);
        }
    }
}
