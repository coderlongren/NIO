## Java NIO笔记
Java NIO主要由以下的几个核心部分组成 
* Channels
* Buffers
* Slector

---
Channels的主要有下面的几个实现类
* FileChannel
* DatagramChannel
* SocketChannel
* ServerSocketChannel
基本覆盖了 UDP TCP 网络IO，以为文件IO

---
## 缓冲区（Buffer）：在 Java NIO 中负责数据的存取。缓冲区就是数组。用于存储不同数据类型的数据
根据数据类型不同（boolean 除外），提供了相应类型的缓冲区：  
下面是JavaNIO里面几个关键的Buffer的实现类： 
* ByteBuffer
* CharBuffer
* DoubleBuffer
* FloatBuffer
* IntBuffer
* LongBuffer
* ShortBuffer
这些Buffer覆盖了你能通过IO发送的基本数据类型：byte, short, int, long, float, double 和 char。  
 上述缓冲区的管理方式几乎一致，通过 allocate() 获取缓冲区
```
put() 存入数据到缓冲区
get() 获取缓冲区的数据
```

### buffer的四个核心属性
capacity : 容量，表示缓冲区中的最大存储数据的容量，一旦声明，不能改变  
limit : 界限，表示缓冲区中可以操作数据的大小，(limit之后的数据是不能读写的)  
position : 位置，表示缓冲区正在操作的位置 position  
mark: 标记，表示记录当前 position 的位置。可以通过 reset() 恢复到 mark 的位置  

0 <= mark <= position <= limit <= capacity  

### 直接对缓冲区与非直接缓冲区别
* 如果只用非直接缓冲区，那么执行read()请求，数据将会从OS系统内核中，将数据COPY进JVM内存空间中，然后我们的程序才能读取这些数据
* 直接缓冲区，将会省去中间的数据copy过程，但事将会引发不安全的操作，将会消耗大量的内存空间而且内存将不受用户控制。适用于，那些长期占据内存空间时，使用
则 Java 虚拟机会尽最大努力直接在此缓冲区上执行本机 I/O 操作。也就是说，在每次调用基础操作系统的一个本机 I/O 操作之前（或之后），
虚拟机都会尽量避免将缓冲区的内容复制到中间缓冲区中（或从中间缓冲区中复制内容）。
直接缓冲区的调用过程：
* 直接字节缓冲区可以通过调用此类的 allocateDirect() 工厂方法来创建。此方法返回的缓冲区进行分配和取消分配所需成本通常高于非直接缓冲区。直接缓冲区的内容可以驻留在常规的垃圾回收堆之外，因此，它们对应用程序的内存需求量造成的影响可能并不明显。所以，建议将直接缓冲区主要分配给那些易受基础系统的本机IO操作影响的大型、持久的缓冲区。一般情况下，最好仅在直接缓冲区能在程序性能方面带来明显好
处时分配它们。

一、通道（Channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 * 
 * 二、通道的主要实现类
 * 	java.nio.channels.Channel 接口：
 * 		|--FileChannel
 * 		|--SocketChannel
 * 		|--ServerSocketChannel
 * 		|--DatagramChannel
 * 
 * 三、获取通道
 * 1. Java 针对支持通道的类提供了 getChannel() 方法
 * 		本地 IO：
 * 		FileInputStream/FileOutputStream
 * 		RandomAccessFile
 * 
 * 		网络IO：
 * 		Socket
 * 		ServerSocket
 * 		DatagramSocket
 * 		
 * 2. 在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 * 3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 * 
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 * 
 * 五、分散(Scatter)与聚集(Gather)
 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
 * 
 * 六、字符集：Charset
 * 编码：字符串 -> 字节数组
 * 解码：字节数组  -> 字符串
 * 
 */
 ## NIO在网络通信的核心技术
 
 
