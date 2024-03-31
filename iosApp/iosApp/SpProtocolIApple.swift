import Foundation
import NIO
import NIOFoundationCompat
import shared

class SpProtocolApple: SyncplayProtocol {
    private var channel: Channel?
    private var eventLoopGroup: EventLoopGroup?


    override func connectSocket() {
        let group = MultiThreadedEventLoopGroup(numberOfThreads: System.coreCount)
        eventLoopGroup = group

        print("Bootstrapping...")
        
        let bootstrap = ClientBootstrap(group: group)
            .connectTimeout(TimeAmount.seconds(10))
            .channelInitializer { channel in
                channel.pipeline.addHandler(Reader(p: self))
            }

        let host = session.serverHost
        let port = Int(session.serverPort)
        
        do {
            channel = try bootstrap.connect(host: host, port: port).wait()
        } catch {
            print(error)
            syncplayCallback?.onConnectionFailed()
            return
        }

        if channel != nil {
            print("Connected!")
            syncplayCallback?.onConnected()
        }
    }

    
    override func isSocketValid() -> Bool {
        return channel?.isActive ?? false
    }

    override func endConnection(terminating: Bool) {
        try? channel?.close().wait()
        try? eventLoopGroup?.syncShutdownGracefully()
        
        if terminating {
            terminateScope()
        }
    }

    override func writeActualString(s: String) {
        guard let channel = channel else {
            syncplayCallback?.onDisconnected()
            return
        }
        
        let data = s.data(using: .utf8)!
        let buffer = channel.allocator.buffer(bytes: data)
        
        channel.writeAndFlush(buffer).whenComplete { result in
            do {
                try result.get()
            } catch {
                self.syncplayCallback?.onDisconnected()
                print("Error writing to channel: \(error)")
            }
        }
    }

    override func upgradeTls() {
        // TLS setup for SwiftNIO
    }
}

class Reader: ChannelInboundHandler {
    typealias InboundIn = ByteBuffer
    let p: SpProtocolApple
    
    init(p: SpProtocolApple) {
        self.p = p
    }
    
    func channelRead(context: ChannelHandlerContext, data: NIOAny) {
        var buffer = unwrapInboundIn(data)
        let readableBytes = buffer.readableBytes
        
        if let received = buffer.readString(length: readableBytes) {
            self.p.routeInScope() {
                JsonHandlerKt.handleJson(protocol: self.p, json: received)
            }
        }
    }
    
    func channelReadComplete(context: ChannelHandlerContext) {
        context.flush()
    }

    func errorCaught(context: ChannelHandlerContext, error: Error) {
        print("Reader exception: \(error)")
        p.syncplayCallback?.onDisconnected()
        context.close(promise: nil)
    }
}
