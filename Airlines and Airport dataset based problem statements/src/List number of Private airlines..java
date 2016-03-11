import java.io.IOException;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class private_airlines_count {

	public static class mymapper extends Mapper<Object,Text,Text,IntWritable>{
		
		protected void map(Object key,Text value,Context context) throws IOException, InterruptedException{
			
			CharSequence c="private";
			if(value.toString().toLowerCase().contains(c)){
				context.write(new Text("private"),new IntWritable(1));
			}
		}
	}
	public static class myreducer extends Reducer<Text,IntWritable,Text,IntWritable>{
		int count=0;
		protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException{
			
			for(IntWritable t:values){
				count++;
			}
			context.write(new Text("private airlines"), new IntWritable(count));
		}
	}
	
	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException
	{
		Configuration conf=new Configuration();
		Job job=Job.getInstance(conf,"private_airlines");
		job.setJarByClass(private_airlines_count.class);
		job.setMapperClass(mymapper.class);
		job.setReducerClass(myreducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);
	}
	
	

}
