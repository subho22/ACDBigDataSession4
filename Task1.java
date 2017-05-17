package Session4;

import org.apache.hadoop.fs.Path;

import java.io.IOException;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat; 
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat; 
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat; 
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;


public class Task1 {
	
public static class Map extends Mapper<LongWritable,Text,LongWritable,Text> {

	private Text word =new Text();
	
public void map(LongWritable key, Text value,Context context) throws IOException,InterruptedException{
String linearray[] = value.toString().split("\\|");

if(!linearray[0].equals("NA") && !linearray[1].equals("NA")) 
{
	word.set(value.toString());
  
    context.write(key,word);
}

}
}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "Task1");
		job.setJarByClass(Task1.class);
        
		job.setMapperClass(Map.class);
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(Text.class);
         
		
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(Text.class);
		
		job.setNumReduceTasks(0);
		 
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(args[0])); 
		FileOutputFormat.setOutputPath(job,new Path(args[1]));
		
		/*
		Path out=new Path(args[1]);
		out.getFileSystem(conf).delete(out);
		*/
		
		job.waitForCompletion(true);
	}
}
