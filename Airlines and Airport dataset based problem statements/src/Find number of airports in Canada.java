import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class modified_no_of_airport {

	public static class mymapper extends Mapper<Object,Text,Text,Text>{
		protected void map(Object key,Text value,Context context) throws IOException, InterruptedException{
			String data[]=value.toString().split(",");
		CharSequence c="canada";
			if(data[3].toLowerCase().contains(c)){
				context.write(new Text("canada"), new Text(data[1]));
			}
		}
	}
	
	public static class myreducer extends Reducer<Text,Text,Text,IntWritable>{
		
		protected void reduce(Text key,Iterable<Text> values,Context context) throws IOException, InterruptedException{
			int count=0;
			for(Text t:values){
				count=count+1;
			}
			context.write(key, new IntWritable(count));
		}
	}
	
	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException
	{
		Configuration conf=new Configuration();
		Job job=Job.getInstance(conf,"no_of_airports");
		job.setJarByClass(modified_no_of_airport.class);
		job.setMapperClass(mymapper.class);
		job.setReducerClass(myreducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);

	}

}
