package com.yuroyami.syncplay.lyricist.localizations

import com.yuroyami.syncplay.lyricist.Strings
import com.yuroyami.syncplay.utils.format

val JaStrings = object : Strings {
    override val yes = "はい"
    override val no = "いいえ"
    override val okay = "OK"
    override val cancel = "キャンセル"
    override val dontShowAgain = "次回以降表示しない"
    override val play = "再生"
    override val pause = "一時停止"
    override val delete = "削除"
    override val confirm = "確認"
    override val done = "完了"
    override val close = "閉じる"
    override val off = "オフ"
    override val on = "オン"
    override val tabConnect = "接続"
    override val tabSettings = "設定"
    override val tabAbout = "情報"
    override val connectUsernameA = "ユーザー名を入力:"
    override val connectUsernameB = "ユーザー名"
    override val connectUsernameC = "お好きな名前"
    override val connectRoomnameA = "ルーム名を入力:"
    override val connectRoomnameB = "ルーム名"
    override val connectRoomnameC = "友達と視聴するルーム"
    override val connectServerA = "Syncplay サーバーを選択:"
    override val connectServerB = "サーバーアドレス"
    override val connectServerC = "友達と同じサーバーに参加してください。"
    override val connectButtonJoin = "ルームに参加"
    override val connectButtonSaveshortcut = "現在の設定をホームショートカットとして保存"
    override val connectButtonCurrentEngine = { p0: String -> "現在のエンジン: %s".format(p0) }
    override val connectFootnote = "Syncplayの非公式Androidクライアント"
    override val connectUsernameEmptyError = "ユーザー名は空にできません"
    override val connectRoomnameEmptyError = "ルーム名は空にできません"
    override val connectAddressEmptyError = "サーバーアドレスは空にできません"
    override val connectPortEmptyError = "ポート番号を入力してください"
    override val connectEnterCustomServer = "カスタムサーバーを入力"
    override val connectCustomServerPassword = "パスワード（不要な場合は空白）"
    override val connectPort = "ポート"
    override val connectNightmodeswitch = "デイ/ナイトモードを切り替える"
    override val connectSolomode = "ソロモードに入る（ビデオプレーヤーのみ）"
    override val roomSelectedVid = { p0: String -> "選択したビデオファイル：%s".format(p0) }
    override val roomSelectedSub = { p0: String -> "読み込まれた字幕ファイル：%s".format(p0) }
    override val roomSelectedSubError = "無効な字幕ファイルです。サポートされているフォーマットは：'SRT'、'TTML'、'ASS'、'SSA'、'VTT'です"
    override val roomSubErrorLoadVidFirst = "まずビデオを読み込んでください"
    override val roomTypeMessage = "メッセージを入力してください..."
    override val roomReady = "準備完了"
    override val roomNotReady = "未準備"
    override val roomPingConnected = { p0: String -> "接続済み - PING：%s ms".format(p0) }
    override val roomPingDisconnected = "切断されました"
    override val roomOverflowSub = "字幕ファイルを読み込む..."
    override val roomOverflowMsghistory = "メッセージ履歴"
    override val roomOverflowSettings = "設定"
    override val roomEmptyMessageError = "何か入力してください！"
    override val roomAttemptingConnect = { p0: String, p1: String -> "%1s:%2sへの接続を試みています".format(p0, p1) }
    override val roomConnectedToServer = "サーバーに接続しました。"
    override val roomConnectionFailed = "接続に失敗しました。"
    override val roomAttemptingReconnection = "サーバーへの接続が失われました。再接続を試みています。"
    override val roomGuyPlayed = { p0: String -> "%sが再生を再開しました".format(p0) }
    override val roomGuyPaused = { p0: String, p1: String -> "%1sが%2sで一時停止しました".format(p0, p1) }
    override val roomSeeked = { p0: String, p1: String, p2: String -> "%1sが%2sから%3sにジャンプしました".format(p0, p1, p2) }
    override val roomRewinded = { p0: String -> "%sとの時差により巻き戻しました".format(p0) }
    override val roomGuyLeft = { p0: String -> "%sがルームを退出しました。".format(p0) }
    override val roomGuyJoined = { p0: String -> "%sがルームに参加しました。".format(p0) }
    override val roomIsplayingfile = { p0: String, p1: String, p2: String -> "%1sが'%2s'（%3s）を再生しています".format(p0, p1, p2) }
    override val roomYouJoinedRoom = { p0: String -> "あなたはルームに参加しました：%s".format(p0) }
    override val roomScalingFitScreen = "リサイズモード：画面に合わせる"
    override val roomScalingFixedWidth = "リサイズモード：固定幅"
    override val roomScalingFixedHeight = "リサイズモード：固定高さ"
    override val roomScalingFillScreen = "リサイズモード：画面を埋める"
    override val roomScalingZoom = "リサイズモード：ズーム"
    override val roomSubTrackChanged = { p0: String -> "字幕トラックが変更されました：%s".format(p0) }
    override val roomAudioTrackChanged = { p0: String -> "オーディオトラックが変更されました：%s".format(p0) }
    override val roomAudioTrackNotFound = "オーディオが見つかりませんでした！"
    override val roomSubTrackDisable = "字幕を無効にする"
    override val roomTrackTrack = "トラック"
    override val roomSubTrackNotfound = "字幕が見つかりませんでした！"
    override val roomDetailsCurrentRoom = { p0: String -> "現在のルーム：%s".format(p0) }
    override val roomDetailsNofileplayed = "（再生中のファイルなし）"
    override val roomDetailsFileProperties = { p0: String, p1: String -> "時間：%1s - サイズ：%2s MB".format(p0, p1) }
    override val roomFileMismatchWarningCore = { p0: String -> "あなたのファイルは%sのファイルと以下の点で異なります：".format(p0) }
    override val roomFileMismatchWarningName = "名前。"
    override val roomFileMismatchWarningDuration = "時間。"
    override val roomFileMismatchWarningSize = "サイズ。"
    override val roomSharedPlaylist = "ルームの共有プレイリスト"
    override val roomSharedPlaylistBrief = "ファイルまたはディレクトリをインポートして、プレイリストにファイル名を含めます。ファイル行をクリックすると、すべてのユーザーが再生します。"
    override val roomSharedPlaylistUpdated = { p0: String -> "%sがプレイリストを更新しました".format(p0) }
    override val roomSharedPlaylistChanged = { p0: String -> "%sがプレイリストの現在の選択を変更しました".format(p0) }
    override val roomSharedPlaylistNoDirectories = "共有プレイリスト用のメディアディレクトリが指定されていません。ファイルを手動で追加してください。"
    override val roomSharedPlaylistNotFound = "Syncplayは、メディアディレクトリで現在再生されている共有プレイリストのファイルを見つけられませんでした。"
    override val roomSharedPlaylistNotFeatured = "このルームまたはサーバーには共有プレイリストの機能がありません。"
    override val roomSharedPlaylistButtonAddFile = "プレイリストの末尾にファイルを追加"
    override val roomSharedPlaylistButtonAddFolder = "プレイリストにフォルダを追加（およびメディアディレクトリ）"
    override val roomSharedPlaylistButtonAddUrl = "プレイリストの末尾にURLを追加"
    override val roomSharedPlaylistButtonShuffle = "プレイリスト全体をシャッフル"
    override val roomSharedPlaylistButtonShuffleRest = "残りのプレイリストをシャッフル"
    override val roomSharedPlaylistButtonOverflow = "その他の共有プレイリスト設定"
    override val roomSharedPlaylistButtonPlaylistImport = "ファイルからプレイリストを読み込む"
    override val roomSharedPlaylistButtonPlaylistImportNShuffle = "ファイルからプレイリストを読み込んでシャッフル"
    override val roomSharedPlaylistButtonPlaylistExport = "プレイリストをファイルに保存"
    override val roomSharedPlaylistButtonSetMediaDirectories = "メディアディレクトリを設定"
    override val roomSharedPlaylistButtonSetTrustedDomains = "信頼されたドメインを設定"
    override val roomSharedPlaylistButtonUndo = "最後の変更を元に戻す"
    override val roomButtonDescAspectRatio = "アスペクト比"
    override val roomButtonDescSharedPlaylist = "共有プレイリスト"
    override val roomButtonDescAudioTracks = "オーディオトラック"
    override val roomButtonDescSubtitleTracks = "字幕トラック"
    override val roomButtonDescRewind = "巻き戻し"
    override val roomButtonDescToggle = "."
    override val roomButtonDescPlay = "再生"
    override val roomButtonDescPause = "一時停止"
    override val roomButtonDescFfwd = "早送り"
    override val roomButtonDescAdd = "メディアファイルを追加"
    override val roomButtonDescLock = "タッチロック"
    override val roomButtonDescMore = "その他の設定"
    override val roomAddmediaOffline = "携帯電話のストレージから"
    override val roomAddmediaOnline = "ネットワークURLから"
    override val roomAddmediaOnlineUrl = "URLアドレス"
    override val roomSkipMinuteAndHalfButton = "1分30秒スキップ"
    override val mediaDirectories = "共有プレイリストのメディアディレクトリ"
    override val mediaDirectoriesBrief = "Syncplayは、ここで指定したメディアディレクトリを検索して、共有プレイリストが再生している名前を見つけるために使用します。検索操作はスロットル制御される可能性があり、非常に遅くなる可能性があるため、小さなディレクトリを指定すると良いです。"
    override val mediaDirectoriesSettingSummary = "Syncplayは、ここで指定したメディアディレクトリを検索して、共有プレイリストが再生している名前を見つけるために使用します。"
    override val mediaDirectoriesSave = "保存して終了"
    override val mediaDirectoriesClearAll = "すべてクリア"
    override val mediaDirectoriesClearAllConfirm = "リストをクリアしてもよろしいですか？"
    override val mediaDirectoriesAddFolder = "フォルダを追加"
    override val mediaDirectoriesDelete = "リストから削除"
    override val mediaDirectoriesShowFullPath = "フルパスを表示"

    override val settingsCategGeneral = "一般"
    override val settingsCategExoplayer = "Exoplayer"
    override val settingsCategLanguage = "言語"
    override val settingsCategSyncing = "同期"
    override val settingsCategNetwork = "ネットワーク"
    override val settingsCategAdvanced = "高度な設定"
    override val uisettingCategChatColors = "チャットの色"
    override val uisettingCategChatProperties = "チャットのプロパティ"
    override val uisettingCategPlayerSettings = "ビデオプレーヤーの設定"


    override val settingNightModeTitle = "ナイトモード"
    override val settingNightModeSummary = "ナイトモードの動作を選択します。"
    override val settingRememberJoinInfoTitle = "参加情報を覚えておく"
    override val settingRememberJoinInfoSummary = "デフォルトで有効です。これにより、SyncPlayは最後に使用したユーザー名、ルーム名、および公式サーバーを保存できます。"
    override val settingDisplayLanguageTitle = "表示言語"
    override val settingDisplayLanguageSummry = "Syncplayが表示される言語を選択します。"
    override val settingDisplayLanguageToast = "言語が変更されました。設定が完全に有効になるにはアプリを再起動してください。"
    override val settingAudioDefaultLanguageTitle = "優先オーディオトラック言語"
    override val settingAudioDefaultLanguageSummry = "ここで設定した言語コードを使用してオーディオトラックを自動的にロードします。たとえば、英語のコードは 'en-US'、日本語は 'ja-JP' です。詳細は 'IETF BCP 47 codes' で調べてください。"
    override val settingCcDefaultLanguageTitle = "優先字幕言語"
    override val settingCcDefaultLanguageSummry = "ここで設定した言語コードを使用して字幕トラックを自動的にロードします。たとえば、英語のコードは 'en-US'、日本語は 'ja-JP' です。詳細は 'IETF BCP 47 codes' で調べてください。"
    override val settingUseBufferTitle = "カスタムバッファサイズの使用"
    override val settingUseBufferSummary = "再生前および再生中のプレイヤーのビデオ読み込み時間に満足していない場合、カスタムバッファサイズを使用できます（自己責任で使用してください）。"
    override val settingMaxBufferTitle = "カスタム最大バッファサイズ"
    override val settingMaxBufferSummary = "デフォルトは30（30000ミリ秒）です。これはビデオを再生する前の最大バッファサイズを決定します。これについてわからない場合は変更しないでください。"
    override val settingMinBufferSummary = "デフォルトは15（15000ミリ秒）です。この値を減らしてビデオをより速く再生することができますが、プレイヤーが失敗したり、クラッシュする可能性があります。自己責任で変更してください。"
    override val settingMinBufferTitle = "カスタム最小バッファサイズ"
    override val settingPlaybackBufferSummary = "デフォルトは2500ミリ秒です。これはビデオをシークまたは再開する際のバッファサイズを表します。ビデオをシークするときの小さな遅延に満足していない場合は、これを変更してください。"
    override val settingPlaybackBufferTitle = "カスタム再生バッファサイズ（ミリ秒）"
    override val settingReadyFirsthandSummary = "これを有効にすると、部屋に入ると自動的に準備完了に設定されます。"
    override val settingReadyFirsthandTitle = "最初に準備完了にする"
    override val settingRewindThresholdSummary = "ここで選択した値で誰かが遅れている場合、ビデオはその遅れに合わせて巻き戻されます。"
    override val settingRewindThresholdTitle = "巻き戻ししきい値"
    override val settingTlsSummary = "サーバーがTLSセキュア接続をサポートしている場合、Syncplay AndroidはTLS経由で接続を試みます。 （まだ使用できません）"
    override val settingTlsTitle = "セキュア接続の使用（TLSv1.3）[近日公開予定]"
    override val settingResetdefaultTitle = "デフォルト設定にリセット"
    override val settingResetdefaultSummary = "すべてをデフォルト値にリセットします（推奨）"
    override val settingResetdefaultDialog = "この画面の設定をクリアしてもよろしいですか？"
    override val settingPauseIfSomeoneLeftTitle = "誰かが去った場合に一時停止"
    override val settingPauseIfSomeoneLeftSummary = "これを有効にすると、視聴中に誰かが部屋を去った場合、再生が一時停止/停止します。"
    override val settingWarnFileMismatchTitle = "ファイル不一致警告"
    override val settingWarnFileMismatchSummary = "デフォルトで有効です。これにより、ファイルをロードする際にグループ内のユーザーと異なるファイル（名前、時間、サイズのいずれか）をロードした場合に警告が表示されます。"
    override val settingFileinfoBehaviourNameTitle = "ファイル名情報の送信"
    override val settingFileinfoBehaviourNameSummary = "他のユーザーに追加したメディアファイル名を表示する方法を選択します。"
    override val settingFileinfoBehaviourSizeTitle = "ファイルサイズ情報の送信"
    override val settingFileinfoBehaviourSizeSummary = "他のユーザーに追加したメディアファイルサイズを表示する方法を選択します。"
    override val uisettingApply = "適用"
    override val uisettingTimestampSummary = "これを無効にすると、チャットメッセージの先頭にタイムスタンプが表示されなくなります。"
    override val uisettingTimestampTitle = "チャットタイムスタンプ"
    override val uisettingMsgoutlineSummary = "これを有効にすると、チャットメッセージに輪郭が付き、背景ビデオとのブレンドが低減されます。"
    override val uisettingMsgoutlineTitle = "チャットメッセージの輪郭"
    override val uisettingMsgshadowSummary = "これを有効にすると、チャットメッセージに影が付き、背景ビデオとのブレンドが低減されます。"
    override val uisettingMsgshadowTitle = "チャットメッセージの影"
    override val uisettingMsgboxactionSummary =
        "有効にすると、キーボードの 'OK' ボタンをクリックするとメッセージが送信されます。これが無効になると、キーボードを閉じるだけで何も起こりません。"
    override val uisettingMsgboxactionTitle = "キーボードのOKボタンの機能"
    override val uisettingOverviewAlphaSummary = "デフォルトは40（ほぼ透明）です。これを変更して、ルーム詳細パネルの背景の透明度を増やして可読性を向上させることができます。"
    override val uisettingOverviewAlphaTitle = "ルーム詳細の背景の透明度"
    override val uisettingMessageryAlphaSummary = "デフォルトは0（透明）です。背景をより不透明にしてメッセージの可読性を向上させるためにこれを増やしてください。"
    override val uisettingMessageryAlphaTitle = "メッセージの背景の透明度"
    override val uisettingMsgsizeSummary = "メッセージのテキストサイズを変更します。デフォルトは10です。"
    override val uisettingMsgsizeTitle = "メッセージフォントサイズ"
    override val uisettingMsgcountSummary = "デフォルトは10です。メッセージ数をこの値に制限します。"
    override val uisettingMsgcountTitle = "メッセージの最大数"
    override val uisettingMsglifeSummary = "チャットメッセージまたはルームメッセージを受信すると、以下に設定された時間の間、フェードアウトを開始します。"
    override val uisettingMsglifeTitle = "チャットメッセージの表示時間"
    override val uisettingTimestampColorSummary = "タイムスタンプのテキスト色をカスタマイズします（デフォルトはピンク）"
    override val uisettingTimestampColorTitle = "タイムスタンプのテキスト色"
    override val uisettingSelfColorSummary = "あなたの名前タグのテキスト色をカスタマイズします（デフォルトはダークレッド）"
    override val uisettingSelfColorTitle = "セルフタグのテキスト色"
    override val uisettingFriendColorSummary = "友達の名前タグのテキスト色をカスタマイズします（デフォルトはブルー）"
    override val uisettingFriendColorTitle = "フレンドタグのテキスト色"
    override val uisettingSystemColorSummary = "システムルームメッセージのテキスト色をカスタマイズします（デフォルトはホワイト）"
    override val uisettingSystemColorTitle = "システムメッセージのテキスト色"
    override val uisettingHumanColorSummary = "ユーザーメッセージのテキスト色をカスタマイズします（デフォルトはホワイト）"
    override val uisettingHumanColorTitle = "ユーザーメッセージのテキスト色"
    override val uisettingErrorColorSummary = "エラーメッセージのテキスト色をカスタマイズします（デフォルトはレッド）"
    override val uisettingErrorColorTitle = "エラーメッセージのテキスト色"
    override val uisettingSubtitleSizeSummary =
        "これはサイドロードされた字幕（ファイルから読み込む場合）のサイズを変更します。デフォルトは16です。ビルトインの字幕のサイズは変更できません。"
    override val uisettingSubtitleSizeTitle = "字幕サイズ"
    override val uisettingSubtitleDelaySummary = "デフォルトは0です。字幕トラックを遅らせたり進めたりします。進めるには負の値を使用します。"
    override val uisettingSubtitleDelayTitle = "字幕遅延（ミリ秒）"
    override val uisettingAudioDelaySummary = "デフォルトは0です。オーディオトラックを遅らせたり進めたりします。進めるには負の値を使用します。"
    override val uisettingAudioDelayTitle = "オーディオトラックの遅延（ミリ秒）"
    override val uisettingSeekForwardJumpSummary = "指定された秒数だけ進むとシークする量を指定します。デフォルトは10秒です。"
    override val uisettingSeekForwardJumpTitle = "シークフォワードジャンプ量（秒）"
    override val uisettingSeekBackwardJumpSummary = "指定された秒数だけ巻き戻すとシークする量を指定します。デフォルトは10秒です。"
    override val uisettingSeekBackwardJumpTitle = "シークバックワードジャンプ量（秒）"
    override val uisettingPipSummary = "アプリを最小化するとウィンドウピクチャインピクチャモードに入るかどうかを指定します。デフォルトはtrueです。"
    override val uisettingPipTitle = "ピクチャインピクチャモード"
    override val uisettingReconnectIntervalSummary = "接続または切断の際の再接続までの待機時間を秒単位で指定します。デフォルトは2秒です。"
    override val uisettingReconnectIntervalTitle = "再接続間隔"
    override val uisettingResetdefaultSummary = "上記のすべての設定をデフォルトにリセットします。"
    override val uisettingResetdefaultTitle = "デフォルト設定にリセット"
    override val settingFileinfoBehaviorA = "生の状態で送信"
    override val settingFileinfoBehaviorB = "ハッシュで送信"
    override val settingFileinfoBehaviorC = "送信しない"
}