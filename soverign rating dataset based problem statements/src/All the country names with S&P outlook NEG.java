import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.io.output.NullWriter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.sun.org.apache.xml.internal.serializer.utils.StringToIntTable;



public class country_outlookNEG {

	static class mymapper extends Mapper<LongWritable,Text,Text, Text>{
		
		protected void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException{
		
			String data[]=value.toString().split("\\t");
			if(data[3].equals("NEG"))
			context.write(new Text("NEG"), new Text(data[1]));
			
		}
	}
	static class myreducer extends Reducer<Text,Text,Text,NullWritable>{
		protected void reduce(Text key, Iterable<Text>values,Context context) throws IOException, InterruptedException{
			for(Text t:values){
				context.write(t,null);
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		Configuration conf=new Configuration();
		Job job=Job.getInstance(conf,"outlookNEG");
		job.setJarByClass(country_outlookNEG.class);
		job.setMapperClass(mymapper.class);
		job.setReducerClass(myreducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);

	}

}
