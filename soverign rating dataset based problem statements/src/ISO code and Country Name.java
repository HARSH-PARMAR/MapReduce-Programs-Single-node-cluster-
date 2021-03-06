import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.sun.org.apache.xml.internal.serializer.utils.StringToIntTable;

public class ISO_countryname {

	static class mymapper extends Mapper<LongWritable,Text,Text, Text>{
		
		protected void map(LongWritable key,Text value,Context context) throws IOException, InterruptedException{
			
			StringTokenizer data=new StringTokenizer(value.toString());
			context.write(new Text(data.nextToken() ),new Text(data.nextToken()) );
		}
	}
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Iso_countryname");
		job.setJarByClass(ISO_countryname.class);
		// TODO: specify a mapper
		job.setMapperClass(mymapper.class);
		// TODO: specify a reducer
		
		// TODO: specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		// TODO: specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		if (!job.waitForCompletion(true))
			return;
	}

}
