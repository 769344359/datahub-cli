package xyz.shakudada.cli;

import com.aliyun.datahub.client.DatahubClient;
import com.aliyun.datahub.client.DatahubClientBuilder;
import com.aliyun.datahub.client.auth.AliyunAccount;
import com.aliyun.datahub.client.common.DatahubConfig;
import com.aliyun.datahub.client.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static class Args {
        public String endPoint = "";
        public String accessId = "";
        public String accessKey = "";
        public String projectNmae = "";
        public String shareId = "";
        public String topic = "";
        public String data = "";
    }

    public static String readLing(String fileName) throws FileNotFoundException {
        System.out.println("reading file " + fileName);
        Scanner sc = new Scanner(new File(fileName));
        if (sc.hasNextLine()) {
            String res = sc.nextLine();
            return res;
        }
        return null;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Args dataHubArgs = new Args();
        for (String arg : args) {

            String[] para = arg.split("=");
            String str = null;
            if (para.length == 2) {
                str = String.format("参数: %s: 值为: %s", para[0], para[1]);
            } else {
                str = String.format("参数: %s: 值为: %s", para[0], null);
            }
            System.out.println(str);
            if (para[0].equals("--topic")) {
                dataHubArgs.topic = para[1];
            } else if (para[0].equals("--project")) {
                dataHubArgs.projectNmae = para[1];
            } else if (para[0].equals("--accessid")) {
                dataHubArgs.accessId = para[1];
            } else if (para[0].equals("--accesskey")) {
                dataHubArgs.accessKey = para[1];
            } else if (para[0].equals("--endpoint")) {
                dataHubArgs.endPoint = para[1];
            } else if (para[0].equals("--shareid")) {
                dataHubArgs.shareId = para[1];
            } else if (para[0].equals("--data")) {
                if (para.length < 2) {
                    dataHubArgs.data = null;
                } else {
                    dataHubArgs.data = readLing(para[1]);
                }
            }
        }
        doSend(dataHubArgs);

    }

    public static void doSend(Args args) {
        // construct record schema
        RecordSchema schema = new RecordSchema();
        schema.addField(new Field("data", FieldType.STRING));

/**
 * or get record schema from server
 * RecordSchema schema = client.getTopic("projectName", "topicName").getRecordSchema();
 */
        DatahubClient client = DatahubClientBuilder.newBuilder()
                .setDatahubConfig(
                        new DatahubConfig(args.endPoint, new AliyunAccount(args.accessId, args.accessKey))
                ).build();
        List<RecordEntry> recordEntries = new ArrayList<>();
        RecordEntry entry = new RecordEntry();

        String json = args.data;
        TupleRecordData data = new TupleRecordData(schema);
        data.setField("data", json);
        entry.setRecordData(data);
        entry.setShardId(args.shareId);
        recordEntries.add(entry);

// put tuple records by shard
        client.putRecords(args.projectNmae, args.topic, recordEntries);

    }

}
