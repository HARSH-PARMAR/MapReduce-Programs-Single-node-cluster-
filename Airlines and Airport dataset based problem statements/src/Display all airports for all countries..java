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

public class all_airports_all_countries {

	public static class mymapper extends Mapper<Object,Text,Text,Text>{
		protected void map(Object key,Text value,Context context) throws IOException, InterruptedException{
			String data[]=value.toString().split(",");
			
				context.write(new Text(data[3]), new Text(data[1]));
			
		}
	}
	
	public static class myreducer extends Reducer<Text,Text,Text,Text>{
		
		protected void reduce(Text key,Iterable<Text> values,Context context) throws IOException, InterruptedException{
			
			for(Text t:values){
				context.write(key, new Text(t));
			}
			
		}
	}
	
	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException
	{
		Configuration conf=new Configuration();
		Job job=Job.getInstance(conf,"all_airports_all_countries");
		job.setJarByClass(all_airports_all_countries.class);
		job.setMapperClass(mymapper.class);
		job.setReducerClass(myreducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);

	}

}
