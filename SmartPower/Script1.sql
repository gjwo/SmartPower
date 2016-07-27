--<ScriptOptions statementTerminator="GO"/>

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE sysdtssteplog (
	stepexecutionid BIGINT NOT NULL,
	lineagefull UNIQUEIDENTIFIER NOT NULL,
	stepname null NOT NULL,
	stepexecstatus INT,
	stepexecresult INT,
	starttime DATETIME NOT NULL,
	endtime DATETIME,
	elapsedtime FLOAT(53),
	errorcode INT,
	errordescription null,
	progresscount BIGINT,
	CONSTRAINT PK__sysdtsst__58E1496643A1090D PRIMARY KEY (stepexecutionid) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_mi_smo_stage_internal (
	object_type INT NOT NULL,
	urn null NOT NULL,
	property_name null NOT NULL,
	property_value SQL_VARIANT,
	server_instance_name null,
	physical_server_name null
)
GO

CREATE TABLE sysutility_ucp_snapshot_partitions_internal (
	time_id INT,
	latest_consistent_snapshot_id INT
)
GO

CREATE TABLE sysutility_ucp_supported_object_types_internal (
	object_type INT NOT NULL,
	object_name null,
	CONSTRAINT PK_sysutility_ucp_supported_object_types_internal PRIMARY KEY (object_type) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE syspolicy_policy_execution_history_internal (
	history_id BIGINT NOT NULL,
	policy_id INT NOT NULL,
	start_date DATETIME DEFAULT (getdate()) NOT NULL,
	end_date DATETIME,
	result BIT DEFAULT ((0)) NOT NULL,
	is_full_run BIT DEFAULT ((1)) NOT NULL,
	exception_message null,
	exception null,
	CONSTRAINT PK__syspolic__096AA2E94885B9BB PRIMARY KEY (history_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE syscategories (
	category_id INT NOT NULL,
	category_class INT NOT NULL,
	category_type TINYINT NOT NULL,
	name null NOT NULL
)
GO

CREATE TABLE sysdac_history_internal (
	action_id INT NOT NULL,
	sequence_id INT NOT NULL,
	instance_id UNIQUEIDENTIFIER NOT NULL,
	action_type TINYINT NOT NULL,
	action_type_name VARCHAR(19),
	dac_object_type TINYINT NOT NULL,
	dac_object_type_name VARCHAR(8),
	action_status TINYINT NOT NULL,
	action_status_name VARCHAR(11),
	required BIT,
	dac_object_name_pretran null NOT NULL,
	dac_object_name_posttran null NOT NULL,
	sqlscript null,
	payload IMAGE,
	comments TEXT NOT NULL,
	error_string null,
	created_by null DEFAULT ([dbo].[fn_sysdac_get_currentusername]()) NOT NULL,
	date_created DATETIME DEFAULT (getdate()) NOT NULL,
	date_modified DATETIME DEFAULT (getdate()) NOT NULL,
	CONSTRAINT PK_sysdac_history_internal PRIMARY KEY (action_id, sequence_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysdtscategories (
	name null NOT NULL,
	description null,
	id UNIQUEIDENTIFIER NOT NULL,
	parentid UNIQUEIDENTIFIER NOT NULL,
	CONSTRAINT pk_dtscategories PRIMARY KEY (id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE backupmediafamily (
	media_set_id INT NOT NULL,
	family_sequence_number TINYINT NOT NULL,
	media_family_id UNIQUEIDENTIFIER,
	media_count INT,
	logical_device_name null,
	physical_device_name null,
	device_type TINYINT,
	physical_block_size INT,
	mirror TINYINT DEFAULT ((0)) NOT NULL,
	CONSTRAINT PK__backupme__0C13C86F0880433F PRIMARY KEY (media_set_id, family_sequence_number, mirror) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysmail_servertype (
	servertype null NOT NULL,
	is_incoming BIT DEFAULT ((0)) NOT NULL,
	is_outgoing BIT DEFAULT ((1)) NOT NULL,
	last_mod_datetime DATETIME DEFAULT (getdate()) NOT NULL,
	last_mod_user null DEFAULT (suser_sname()) NOT NULL,
	CONSTRAINT SYSMAIL_SERVERTYPE_TypeMustBeUnique PRIMARY KEY (servertype) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE syspolicy_policy_categories_internal (
	policy_category_id INT NOT NULL,
	name null NOT NULL,
	mandate_database_subscriptions BIT DEFAULT ((1)) NOT NULL,
	CONSTRAINT PK_syspolicy_policy_categories PRIMARY KEY (policy_category_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysjobstepslogs (
	log_id INT NOT NULL,
	log null NOT NULL,
	date_created DATETIME DEFAULT (getdate()) NOT NULL,
	date_modified DATETIME DEFAULT (getdate()) NOT NULL,
	log_size BIGINT,
	step_uid UNIQUEIDENTIFIER NOT NULL,
	CONSTRAINT PK__sysjobst__9E2397E0239E4DCF PRIMARY KEY (log_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_mi_configuration_internal (
	configuration_id INT NOT NULL,
	ucp_instance_name null,
	mdw_database_name null,
	CONSTRAINT pk_sysutility_mi_configuration_internal_configuration_id PRIMARY KEY (configuration_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE log_shipping_secondary_databases (
	secondary_database null NOT NULL,
	secondary_id UNIQUEIDENTIFIER NOT NULL,
	restore_delay INT NOT NULL,
	restore_all BIT NOT NULL,
	restore_mode BIT NOT NULL,
	disconnect_users BIT NOT NULL,
	block_size INT,
	buffer_count INT,
	max_transfer_size INT,
	last_restored_file null,
	last_restored_date DATETIME,
	CONSTRAINT PK__log_ship__093E1AB51372D2FE PRIMARY KEY (secondary_database) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_smo_servers_stub (
	urn null,
	powershell_path null,
	processing_time null,
	batch_time null,
	AuditLevel SMALLINT,
	BackupDirectory null,
	BrowserServiceAccount null,
	BrowserStartMode SMALLINT,
	BuildClrVersionString null,
	BuildNumber INT,
	Collation null,
	CollationID INT,
	ComparisonStyle INT,
	ComputerNamePhysicalNetBIOS null,
	DefaultFile null,
	DefaultLog null,
	Edition null,
	EngineEdition SMALLINT,
	ErrorLogPath null,
	FilestreamShareName null,
	InstallDataDirectory null,
	InstallSharedDirectory null,
	InstanceName null,
	IsCaseSensitive BIT,
	IsClustered BIT,
	IsFullTextInstalled BIT,
	IsSingleUser BIT,
	Language null,
	MailProfile null,
	MasterDBLogPath null,
	MasterDBPath null,
	MaxPrecision TINYINT,
	Name null,
	NamedPipesEnabled BIT,
	NetName null,
	NumberOfLogFiles INT,
	OSVersion null,
	PerfMonMode SMALLINT,
	PhysicalMemory INT,
	Platform null,
	Processors SMALLINT,
	ProcessorUsage INT,
	Product null,
	ProductLevel null,
	ResourceVersionString null,
	RootDirectory null,
	ServerType SMALLINT,
	ServiceAccount null,
	ServiceInstanceId null,
	ServiceName null,
	ServiceStartMode SMALLINT,
	SqlCharSet SMALLINT,
	SqlCharSetName null,
	SqlDomainGroup null,
	SqlSortOrder SMALLINT,
	SqlSortOrderName null,
	Status SMALLINT,
	TapeLoadWaitTime INT,
	TcpEnabled BIT,
	VersionMajor INT,
	VersionMinor INT,
	VersionString null
)
GO

CREATE TABLE log_shipping_monitor_error_detail (
	agent_id UNIQUEIDENTIFIER NOT NULL,
	agent_type TINYINT NOT NULL,
	session_id INT NOT NULL,
	database_name null,
	sequence_number INT NOT NULL,
	log_time DATETIME NOT NULL,
	log_time_utc DATETIME NOT NULL,
	message null NOT NULL,
	source null NOT NULL,
	help_url null NOT NULL
)
GO

CREATE TABLE sysdownloadlist (
	instance_id INT NOT NULL,
	source_server null NOT NULL,
	operation_code TINYINT NOT NULL,
	object_type TINYINT NOT NULL,
	object_id UNIQUEIDENTIFIER NOT NULL,
	target_server null NOT NULL,
	error_message null DEFAULT CREATE DEFAULT default_sdl_error_message AS NULL,
	date_posted DATETIME DEFAULT CREATE DEFAULT default_current_date AS GETDATE() NOT NULL,
	date_downloaded DATETIME,
	status TINYINT DEFAULT CREATE DEFAULT default_zero AS 0 NOT NULL,
	deleted_object_name null
)
GO

CREATE TABLE sysjobs (
	job_id UNIQUEIDENTIFIER NOT NULL,
	originating_server_id INT NOT NULL,
	name null NOT NULL,
	enabled TINYINT NOT NULL,
	description null,
	start_step_id INT NOT NULL,
	category_id INT NOT NULL,
	owner_sid VARBINARY(85) NOT NULL,
	notify_level_eventlog INT NOT NULL,
	notify_level_email INT NOT NULL,
	notify_level_netsend INT NOT NULL,
	notify_level_page INT NOT NULL,
	notify_email_operator_id INT NOT NULL,
	notify_netsend_operator_id INT NOT NULL,
	notify_page_operator_id INT NOT NULL,
	delete_level INT NOT NULL,
	date_created DATETIME NOT NULL,
	date_modified DATETIME NOT NULL,
	version_number INT NOT NULL,
	CONSTRAINT clust UNIQUE (job_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE spt_fallback_usg (
	xserver_name VARCHAR(30) NOT NULL,
	xdttm_ins DATETIME NOT NULL,
	xdttm_last_ins_upd DATETIME NOT NULL,
	xfallback_vstart INT,
	dbid SMALLINT NOT NULL,
	segmap INT NOT NULL,
	lstart INT NOT NULL,
	sizepg INT NOT NULL,
	vstart INT NOT NULL
)
GO

CREATE TABLE sysutility_ucp_health_policies_internal (
	health_policy_id INT NOT NULL,
	policy_name null NOT NULL,
	rollup_object_urn null NOT NULL,
	rollup_object_type INT NOT NULL,
	target_type INT NOT NULL,
	resource_type INT NOT NULL,
	utilization_type INT NOT NULL,
	utilization_threshold FLOAT(53) NOT NULL,
	is_global_policy BIT DEFAULT ((0)),
	CONSTRAINT PK_sysutility_ucp_policies_internal_id PRIMARY KEY (health_policy_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE syscollector_tsql_query_collector (
	collection_set_uid UNIQUEIDENTIFIER NOT NULL,
	collection_set_id INT NOT NULL,
	collection_item_id INT NOT NULL,
	collection_package_id UNIQUEIDENTIFIER NOT NULL,
	upload_package_id UNIQUEIDENTIFIER NOT NULL
)
GO

CREATE TABLE sysutility_ucp_aggregated_mi_health_internal (
	mi_count INT DEFAULT ((0)) NOT NULL,
	mi_healthy_count INT DEFAULT ((0)) NOT NULL,
	mi_unhealthy_count INT DEFAULT ((0)) NOT NULL,
	mi_over_utilize_count INT DEFAULT ((0)) NOT NULL,
	mi_under_utilize_count INT DEFAULT ((0)) NOT NULL,
	mi_on_over_utilized_computer_count INT DEFAULT ((0)) NOT NULL,
	mi_on_under_utilized_computer_count INT DEFAULT ((0)) NOT NULL,
	mi_with_files_on_over_utilized_volume_count INT DEFAULT ((0)) NOT NULL,
	mi_with_files_on_under_utilized_volume_count INT DEFAULT ((0)) NOT NULL,
	mi_with_over_utilized_file_count INT DEFAULT ((0)) NOT NULL,
	mi_with_under_utilized_file_count INT DEFAULT ((0)) NOT NULL,
	mi_with_over_utilized_processor_count INT DEFAULT ((0)) NOT NULL,
	mi_with_under_utilized_processor_count INT DEFAULT ((0)) NOT NULL,
	set_number INT DEFAULT ((0)) NOT NULL
)
GO

CREATE TABLE sysdtstasklog (
	stepexecutionid BIGINT NOT NULL,
	sequenceid INT NOT NULL,
	errorcode INT NOT NULL,
	description null,
	CONSTRAINT PK__sysdtsta__FDDFDAA74865BE2A PRIMARY KEY (stepexecutionid, sequenceid) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_space_utilization_stub (
	processing_time null NOT NULL,
	aggregation_type TINYINT NOT NULL,
	object_type TINYINT NOT NULL,
	virtual_server_name null DEFAULT ('') NOT NULL,
	server_instance_name null DEFAULT ('') NOT NULL,
	volume_device_id null DEFAULT ('') NOT NULL,
	database_name null DEFAULT ('') NOT NULL,
	filegroup_name null DEFAULT ('') NOT NULL,
	dbfile_name null DEFAULT ('') NOT NULL,
	used_space_bytes REAL,
	allocated_space_bytes REAL,
	total_space_bytes REAL,
	available_space_bytes REAL
)
GO

CREATE TABLE sysmaintplan_log (
	task_detail_id UNIQUEIDENTIFIER NOT NULL,
	plan_id UNIQUEIDENTIFIER,
	subplan_id UNIQUEIDENTIFIER,
	start_time DATETIME,
	end_time DATETIME,
	succeeded BIT,
	logged_remotely BIT DEFAULT ((0)) NOT NULL,
	source_server_name null,
	plan_name null,
	subplan_name null,
	CONSTRAINT PK_sysmaintplan_taskdetail_id PRIMARY KEY (task_detail_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sqlagent_info (
	attribute null NOT NULL,
	value null NOT NULL
)
GO

CREATE TABLE syspolicy_execution_internal (
	policy_id INT,
	synchronous BIT,
	event_data null
)
GO

CREATE TABLE sysutility_ucp_cpu_utilization_stub (
	processing_time null,
	aggregation_type TINYINT NOT NULL,
	object_type TINYINT NOT NULL,
	physical_server_name null DEFAULT ('') NOT NULL,
	server_instance_name null DEFAULT ('') NOT NULL,
	database_name null DEFAULT ('') NOT NULL,
	percent_total_cpu_utilization REAL
)
GO

CREATE TABLE sysjobsteps (
	job_id UNIQUEIDENTIFIER NOT NULL,
	step_id INT NOT NULL,
	step_name null NOT NULL,
	subsystem null NOT NULL,
	command null,
	flags INT NOT NULL,
	additional_parameters null,
	cmdexec_success_code INT NOT NULL,
	on_success_action TINYINT NOT NULL,
	on_success_step_id INT NOT NULL,
	on_fail_action TINYINT NOT NULL,
	on_fail_step_id INT NOT NULL,
	server null,
	database_name null,
	database_user_name null,
	retry_attempts INT NOT NULL,
	retry_interval INT NOT NULL,
	os_run_priority INT NOT NULL,
	output_file_name null,
	last_run_outcome INT NOT NULL,
	last_run_duration INT NOT NULL,
	last_run_retries INT NOT NULL,
	last_run_date INT NOT NULL,
	last_run_time INT NOT NULL,
	proxy_id INT,
	step_uid UNIQUEIDENTIFIER,
	CONSTRAINT nc2 UNIQUE (step_uid) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE syspolicy_target_set_levels_internal (
	target_set_level_id INT NOT NULL,
	target_set_id INT NOT NULL,
	type_skeleton null NOT NULL,
	condition_id INT,
	level_name null NOT NULL,
	CONSTRAINT PK_syspolicy_target_set_levels_internal PRIMARY KEY (target_set_level_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_mi_dac_execution_statistics_internal (
	dac_instance_name null NOT NULL,
	database_name null NOT NULL,
	database_id INT NOT NULL,
	date_created DATETIME,
	description null,
	first_collection_time null,
	last_collection_time null,
	last_upload_time null,
	lifetime_cpu_time_ms BIGINT,
	cpu_time_ms_at_last_upload BIGINT,
	CONSTRAINT PK_sysutility_mi_dac_execution_statistics_internal PRIMARY KEY (dac_instance_name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_mi_session_statistics_internal (
	collection_time null NOT NULL,
	session_id INT NOT NULL,
	dac_instance_name null NOT NULL,
	database_name null NOT NULL,
	login_time DATETIME NOT NULL,
	cumulative_cpu_ms INT NOT NULL,
	CONSTRAINT PK_sysutility_mi_session_statistics_internal PRIMARY KEY (collection_time, session_id, dac_instance_name, database_name, login_time) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE systargetservergroupmembers (
	servergroup_id INT NOT NULL,
	server_id INT NOT NULL
)
GO

CREATE TABLE syspolicy_conditions_internal (
	condition_id INT NOT NULL,
	name null NOT NULL,
	date_created DATETIME DEFAULT (getdate()),
	description null DEFAULT ('') NOT NULL,
	created_by null DEFAULT (suser_sname()) NOT NULL,
	modified_by null,
	date_modified DATETIME,
	facet_id INT,
	expression null,
	is_name_condition SMALLINT DEFAULT ((0)),
	obj_name null,
	is_system BIT DEFAULT ((0)) NOT NULL,
	CONSTRAINT PK_syspolicy_conditions PRIMARY KEY (condition_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysalerts (
	id INT NOT NULL,
	name null NOT NULL,
	event_source null NOT NULL,
	event_category_id INT,
	event_id INT,
	message_id INT NOT NULL,
	severity INT NOT NULL,
	enabled TINYINT NOT NULL,
	delay_between_responses INT NOT NULL,
	last_occurrence_date INT NOT NULL,
	last_occurrence_time INT NOT NULL,
	last_response_date INT NOT NULL,
	last_response_time INT NOT NULL,
	notification_message null,
	include_event_description TINYINT NOT NULL,
	database_name null,
	event_description_keyword null,
	occurrence_count INT NOT NULL,
	count_reset_date INT NOT NULL,
	count_reset_time INT NOT NULL,
	job_id UNIQUEIDENTIFIER NOT NULL,
	has_notification INT NOT NULL,
	flags INT NOT NULL,
	performance_condition null,
	category_id INT NOT NULL
)
GO

CREATE TABLE syspolicy_policies_internal (
	policy_id INT NOT NULL,
	name null NOT NULL,
	condition_id INT NOT NULL,
	root_condition_id INT,
	date_created DATETIME DEFAULT (getdate()) NOT NULL,
	execution_mode INT DEFAULT ((0)) NOT NULL,
	policy_category_id INT,
	schedule_uid UNIQUEIDENTIFIER,
	description null DEFAULT ('') NOT NULL,
	help_text null DEFAULT ('') NOT NULL,
	help_link null DEFAULT ('') NOT NULL,
	object_set_id INT,
	is_enabled BIT DEFAULT ((0)) NOT NULL,
	job_id UNIQUEIDENTIFIER,
	created_by null DEFAULT (suser_sname()) NOT NULL,
	modified_by null,
	date_modified DATETIME,
	is_system BIT DEFAULT ((0)) NOT NULL,
	CONSTRAINT PK_syspolicy_policies PRIMARY KEY (policy_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysmail_mailitems (
	mailitem_id INT NOT NULL,
	profile_id INT NOT NULL,
	recipients TEXT,
	copy_recipients TEXT,
	blind_copy_recipients TEXT,
	subject null,
	from_address TEXT,
	reply_to TEXT,
	body null,
	body_format VARCHAR(20),
	importance VARCHAR(6),
	sensitivity VARCHAR(12),
	file_attachments null,
	attachment_encoding VARCHAR(20),
	query null,
	execute_query_database null,
	attach_query_result_as_file BIT,
	query_result_header BIT,
	query_result_width INT,
	query_result_separator CHAR(1),
	exclude_query_output BIT,
	append_query_error BIT,
	send_request_date DATETIME DEFAULT (getdate()) NOT NULL,
	send_request_user null DEFAULT (suser_sname()) NOT NULL,
	sent_account_id INT,
	sent_status TINYINT DEFAULT ((0)),
	sent_date DATETIME,
	last_mod_date DATETIME DEFAULT (getdate()) NOT NULL,
	last_mod_user null DEFAULT (suser_sname()) NOT NULL,
	CONSTRAINT sysmail_mailitems_id_MustBeUnique PRIMARY KEY (mailitem_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE MSdbms_datatype (
	datatype_id INT NOT NULL,
	dbms_id INT NOT NULL,
	type null NOT NULL,
	createparams INT DEFAULT ((0)) NOT NULL,
	CONSTRAINT pk_MSdbms_datatype PRIMARY KEY (datatype_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE log_shipping_monitor_primary (
	primary_id UNIQUEIDENTIFIER NOT NULL,
	primary_server null NOT NULL,
	primary_database null NOT NULL,
	backup_threshold INT NOT NULL,
	threshold_alert INT NOT NULL,
	threshold_alert_enabled BIT NOT NULL,
	last_backup_file null,
	last_backup_date DATETIME,
	last_backup_date_utc DATETIME,
	history_retention_period INT NOT NULL,
	CONSTRAINT PK__log_ship__ED3BE11109E968C4 PRIMARY KEY (primary_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE spt_fallback_dev (
	xserver_name VARCHAR(30) NOT NULL,
	xdttm_ins DATETIME NOT NULL,
	xdttm_last_ins_upd DATETIME NOT NULL,
	xfallback_low INT,
	xfallback_drive CHAR(2),
	low INT NOT NULL,
	high INT NOT NULL,
	status SMALLINT NOT NULL,
	name VARCHAR(30) NOT NULL,
	phyname VARCHAR(127) NOT NULL
)
GO

CREATE TABLE syssubsystems (
	subsystem_id INT NOT NULL,
	subsystem null NOT NULL,
	description_id INT,
	subsystem_dll null,
	agent_exe null,
	start_entry_point null,
	event_entry_point null,
	stop_entry_point null,
	max_worker_threads INT
)
GO

CREATE TABLE syspolicy_facet_events (
	management_facet_id INT NOT NULL,
	event_name null NOT NULL,
	target_type null NOT NULL,
	target_type_alias null NOT NULL
)
GO

CREATE TABLE syspolicy_target_sets_internal (
	target_set_id INT NOT NULL,
	object_set_id INT NOT NULL,
	type_skeleton null NOT NULL,
	type null NOT NULL,
	enabled BIT NOT NULL,
	CONSTRAINT PK_syspolicy_target_sets PRIMARY KEY (target_set_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_mi_volumes_stage_internal (
	volume_device_id null DEFAULT (N'') NOT NULL,
	volume_name null DEFAULT (N'') NOT NULL,
	capacity_mb DECIMAL(20 , 0) DEFAULT ((0.0)) NOT NULL,
	free_space_mb DECIMAL(20 , 0) DEFAULT ((0.0)) NOT NULL,
	server_instance_name null,
	virtual_server_name null,
	physical_server_name null
)
GO

CREATE TABLE log_shipping_primary_secondaries (
	primary_id UNIQUEIDENTIFIER NOT NULL,
	secondary_server null NOT NULL,
	secondary_database null NOT NULL,
	CONSTRAINT pklsprimary_secondaries PRIMARY KEY (primary_id, secondary_server, secondary_database) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE MSdbms_datatype_mapping (
	datatype_mapping_id INT NOT NULL,
	map_id INT NOT NULL,
	dest_datatype_id INT NOT NULL,
	dest_precision BIGINT DEFAULT (NULL),
	dest_scale INT DEFAULT (NULL),
	dest_length BIGINT DEFAULT (NULL),
	dest_nullable BIT DEFAULT (NULL),
	dest_createparams INT DEFAULT ((0)) NOT NULL,
	dataloss BIT DEFAULT ((0)) NOT NULL,
	CONSTRAINT pk_MSdbms_datatype_mapping PRIMARY KEY (datatype_mapping_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_computer_cpu_health_internal (
	physical_server_name null NOT NULL,
	health_state INT NOT NULL,
	set_number INT DEFAULT ((0)) NOT NULL,
	processing_time null DEFAULT (sysdatetimeoffset()) NOT NULL,
	CONSTRAINT PK_sysutility_ucp_computer_cpu_health_internal_name PRIMARY KEY (set_number, physical_server_name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_configuration_internal (
	name null NOT NULL,
	current_value SQL_VARIANT,
	CONSTRAINT PK__sysutili__72E12F1A7093AB15 PRIMARY KEY (name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysmaintplan_logdetail (
	task_detail_id UNIQUEIDENTIFIER NOT NULL,
	line1 null NOT NULL,
	line2 null,
	line3 null,
	line4 null,
	line5 null,
	server_name null NOT NULL,
	start_time DATETIME,
	end_time DATETIME,
	error_number INT,
	error_message null,
	command null,
	succeeded BIT
)
GO

CREATE TABLE sysmail_principalprofile (
	profile_id INT NOT NULL,
	principal_sid VARBINARY(85) NOT NULL,
	is_default BIT DEFAULT ((0)) NOT NULL,
	last_mod_datetime DATETIME DEFAULT (getdate()) NOT NULL,
	last_mod_user null DEFAULT (suser_sname()) NOT NULL,
	CONSTRAINT SYSMAIL_PRINCIPALPROFILE_ProfilePrincipalMustBeUnique PRIMARY KEY (profile_id, principal_sid) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_mi_cpu_stage_internal (
	num_processors INT DEFAULT ((-1)) NOT NULL,
	cpu_name null DEFAULT (N'') NOT NULL,
	cpu_caption null DEFAULT (N'') NOT NULL,
	cpu_family_id DECIMAL(5 , 0) DEFAULT ((-1)) NOT NULL,
	cpu_architecture_id DECIMAL(5 , 0) DEFAULT ((-1)) NOT NULL,
	cpu_max_clock_speed DECIMAL(10 , 0) DEFAULT ((0.0)) NOT NULL,
	cpu_clock_speed DECIMAL(10 , 0) DEFAULT ((0.0)) NOT NULL,
	l2_cache_size DECIMAL(10 , 0) DEFAULT ((0.0)) NOT NULL,
	l3_cache_size DECIMAL(10 , 0) DEFAULT ((0.0)) NOT NULL,
	instance_processor_usage_start_ticks DECIMAL(20 , 0) DEFAULT ((0.0)) NOT NULL,
	instance_collect_time_start_ticks DECIMAL(20 , 0) DEFAULT ((0.0)) NOT NULL,
	computer_processor_idle_start_ticks DECIMAL(20 , 0) DEFAULT ((0.0)) NOT NULL,
	computer_collect_time_start_ticks DECIMAL(20 , 0) DEFAULT ((0.0)) NOT NULL,
	instance_processor_usage_end_ticks DECIMAL(20 , 0) DEFAULT ((0.0)) NOT NULL,
	instance_collect_time_end_ticks DECIMAL(20 , 0) DEFAULT ((0.0)) NOT NULL,
	computer_processor_idle_end_ticks DECIMAL(20 , 0) DEFAULT ((0.0)) NOT NULL,
	computer_collect_time_end_ticks DECIMAL(20 , 0) DEFAULT ((0.0)) NOT NULL,
	server_instance_name null,
	virtual_server_name null,
	physical_server_name null,
	instance_processor_usage_percentage REAL,
	computer_processor_usage_percentage REAL
)
GO

CREATE TABLE sysproxylogin (
	proxy_id INT NOT NULL,
	sid VARBINARY(85),
	flags INT DEFAULT ((0)) NOT NULL
)
GO

CREATE TABLE syspolicy_system_health_state_internal (
	health_state_id BIGINT NOT NULL,
	policy_id INT NOT NULL,
	last_run_date DATETIME NOT NULL,
	target_query_expression_with_id null NOT NULL,
	target_query_expression null NOT NULL,
	result BIT NOT NULL,
	CONSTRAINT PK__syspolic__409BC9E542CCE065 PRIMARY KEY (health_state_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE syssessions (
	session_id INT NOT NULL,
	agent_start_date DATETIME NOT NULL,
	CONSTRAINT PK__syssessi__69B13FDC1BFD2C07 PRIMARY KEY (session_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_mi_database_health_internal (
	server_instance_name null NOT NULL,
	database_name null NOT NULL,
	over_utilized_count INT DEFAULT ((0)) NOT NULL,
	under_utilized_count INT DEFAULT ((0)) NOT NULL,
	set_number INT DEFAULT ((0)) NOT NULL,
	processing_time null DEFAULT (sysdatetimeoffset()) NOT NULL,
	CONSTRAINT PK_sysutility_ucp_mi_database_health_internal_name PRIMARY KEY (set_number, server_instance_name, database_name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysmail_profile (
	profile_id INT NOT NULL,
	name null NOT NULL,
	description null,
	last_mod_datetime DATETIME DEFAULT (getdate()) NOT NULL,
	last_mod_user null DEFAULT (suser_sname()) NOT NULL,
	CONSTRAINT SYSMAIL_PROFILE_IDMustBeUnique PRIMARY KEY (profile_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE syscollector_blobs_internal (
	parameter_name null NOT NULL,
	parameter_value IMAGE NOT NULL,
	CONSTRAINT PK_syscollector_blobs_internal_paremeter_name PRIMARY KEY (parameter_name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysmanagement_shared_registered_servers_internal (
	server_id INT NOT NULL,
	server_group_id INT,
	name null NOT NULL,
	server_name null NOT NULL,
	description null NOT NULL,
	server_type INT NOT NULL,
	CONSTRAINT PK__sysmanag__ED5B5C594D7F7902 PRIMARY KEY (server_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysdbmaintplan_history (
	sequence_id INT NOT NULL,
	plan_id UNIQUEIDENTIFIER DEFAULT ('00000000-0000-0000-0000-000000000000') NOT NULL,
	plan_name null DEFAULT ('All ad-hoc plans') NOT NULL,
	database_name null,
	server_name null DEFAULT (CONVERT([sysname],serverproperty('ServerName'),0)) NOT NULL,
	activity null,
	succeeded BIT DEFAULT ((1)) NOT NULL,
	end_time DATETIME DEFAULT (getdate()) NOT NULL,
	duration INT DEFAULT ((0)),
	start_time DATETIME,
	error_number INT DEFAULT ((0)) NOT NULL,
	message null
)
GO

CREATE TABLE sysdbmaintplan_databases (
	plan_id UNIQUEIDENTIFIER NOT NULL,
	database_name null NOT NULL
)
GO

CREATE TABLE logmarkhistory (
	database_name null NOT NULL,
	mark_name null NOT NULL,
	description null,
	user_name null NOT NULL,
	lsn NUMERIC(25 , 0) NOT NULL,
	mark_time DATETIME NOT NULL
)
GO

CREATE TABLE log_shipping_primaries (
	primary_id INT NOT NULL,
	primary_server_name null NOT NULL,
	primary_database_name null NOT NULL,
	maintenance_plan_id UNIQUEIDENTIFIER,
	backup_threshold INT NOT NULL,
	threshold_alert INT NOT NULL,
	threshold_alert_enabled BIT NOT NULL,
	last_backup_filename null,
	last_updated DATETIME,
	planned_outage_start_time INT NOT NULL,
	planned_outage_end_time INT NOT NULL,
	planned_outage_weekday_mask INT NOT NULL,
	source_directory null,
	CONSTRAINT PK__log_ship__ED3BE1111758727B PRIMARY KEY (primary_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysmaintplan_subplans (
	subplan_id UNIQUEIDENTIFIER NOT NULL,
	subplan_name null NOT NULL,
	subplan_description null,
	plan_id UNIQUEIDENTIFIER NOT NULL,
	job_id UNIQUEIDENTIFIER NOT NULL,
	msx_job_id UNIQUEIDENTIFIER DEFAULT (NULL),
	schedule_id INT,
	msx_plan BIT DEFAULT ((0)) NOT NULL,
	CONSTRAINT PK_sysmaintplan_subplan PRIMARY KEY (subplan_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE syspolicy_policy_execution_history_details_internal (
	detail_id BIGINT NOT NULL,
	history_id BIGINT NOT NULL,
	target_query_expression null NOT NULL,
	target_query_expression_with_id null NOT NULL,
	execution_date DATETIME DEFAULT (getdate()) NOT NULL,
	result BIT NOT NULL,
	result_detail null,
	exception_message null,
	exception null,
	CONSTRAINT PK_syspolicy_policy_execution_history_details_id PRIMARY KEY (history_id, detail_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE backupfilegroup (
	backup_set_id INT NOT NULL,
	name null NOT NULL,
	filegroup_id INT NOT NULL,
	filegroup_guid UNIQUEIDENTIFIER,
	type CHAR(2) NOT NULL,
	type_desc null NOT NULL,
	is_default BIT NOT NULL,
	is_readonly BIT NOT NULL,
	log_filegroup_guid UNIQUEIDENTIFIER,
	CONSTRAINT PK__backupfi__760CD67A12FDD1B2 PRIMARY KEY (backup_set_id, filegroup_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_mi_health_internal (
	mi_name null NOT NULL,
	is_volume_space_over_utilized INT DEFAULT ((0)) NOT NULL,
	is_volume_space_under_utilized INT DEFAULT ((0)) NOT NULL,
	is_computer_processor_over_utilized INT DEFAULT ((0)) NOT NULL,
	is_computer_processor_under_utilized INT DEFAULT ((0)) NOT NULL,
	is_file_space_over_utilized INT DEFAULT ((0)) NOT NULL,
	is_file_space_under_utilized INT DEFAULT ((0)) NOT NULL,
	is_mi_processor_over_utilized INT DEFAULT ((0)) NOT NULL,
	is_mi_processor_under_utilized INT DEFAULT ((0)) NOT NULL,
	is_policy_overridden BIT DEFAULT ((0)) NOT NULL,
	set_number INT DEFAULT ((0)) NOT NULL,
	processing_time null DEFAULT (sysdatetimeoffset()) NOT NULL,
	CONSTRAINT PK_sysutility_ucp_mi_health_internal_name PRIMARY KEY (set_number, mi_name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_computers_stub (
	id INT NOT NULL,
	virtual_server_name null NOT NULL,
	physical_server_name null NOT NULL,
	is_clustered_server INT,
	num_processors INT,
	cpu_name null,
	cpu_caption null,
	cpu_family null,
	cpu_architecture null,
	cpu_max_clock_speed DECIMAL(10 , 0),
	cpu_clock_speed DECIMAL(10 , 0),
	l2_cache_size DECIMAL(10 , 0),
	l3_cache_size DECIMAL(10 , 0),
	urn null,
	powershell_path null,
	processing_time null,
	batch_time null,
	percent_total_cpu_utilization REAL
)
GO

CREATE TABLE backupmediaset (
	media_set_id INT NOT NULL,
	media_uuid UNIQUEIDENTIFIER,
	media_family_count TINYINT,
	name null,
	description null,
	software_name null,
	software_vendor_id INT,
	MTF_major_version TINYINT,
	mirror_count TINYINT,
	is_password_protected BIT,
	is_compressed BIT,
	CONSTRAINT PK__backupme__DAC69E4D04AFB25B PRIMARY KEY (media_set_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE syscollector_collection_sets_internal (
	collection_set_id INT NOT NULL,
	collection_set_uid UNIQUEIDENTIFIER NOT NULL,
	schedule_uid UNIQUEIDENTIFIER,
	name null NOT NULL,
	name_id INT,
	target null,
	is_running BIT DEFAULT ((0)) NOT NULL,
	proxy_id INT,
	is_system BIT NOT NULL,
	collection_job_id UNIQUEIDENTIFIER,
	upload_job_id UNIQUEIDENTIFIER,
	collection_mode SMALLINT DEFAULT ((0)) NOT NULL,
	logging_level SMALLINT DEFAULT ((2)) NOT NULL,
	description null,
	description_id INT,
	days_until_expiration SMALLINT NOT NULL,
	dump_on_any_error BIT DEFAULT ((0)) NOT NULL,
	dump_on_codes null,
	CONSTRAINT PK_syscollector_collection_sets_internal PRIMARY KEY (collection_set_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_datafiles_stub (
	urn null,
	powershell_path null,
	processing_time null,
	batch_time null,
	server_instance_name null NOT NULL,
	database_name null NOT NULL,
	filegroup_name null NOT NULL,
	parent_urn null,
	physical_server_name null NOT NULL,
	volume_name null NOT NULL,
	volume_device_id null NOT NULL,
	Growth REAL,
	GrowthType SMALLINT,
	MaxSize REAL,
	Name null,
	Size REAL,
	UsedSpace REAL,
	FileName null,
	VolumeFreeSpace BIGINT,
	available_space REAL
)
GO

CREATE TABLE sysutility_ucp_filegroups_with_policy_violations_internal (
	server_instance_name null NOT NULL,
	database_name null NOT NULL,
	filegroup_name null NOT NULL,
	policy_id INT NOT NULL,
	set_number INT NOT NULL,
	CONSTRAINT PK_sysutility_ucp_filegroups_with_policy_violations_internal PRIMARY KEY (set_number, policy_id, server_instance_name, database_name, filegroup_name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysmanagement_shared_server_groups_internal (
	server_group_id INT NOT NULL,
	name null NOT NULL,
	description null NOT NULL,
	server_type INT NOT NULL,
	parent_id INT,
	is_system_object BIT DEFAULT ((0)),
	CONSTRAINT PK__sysmanag__752ABCF946D27B73 PRIMARY KEY (server_group_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE backupset (
	backup_set_id INT NOT NULL,
	backup_set_uuid UNIQUEIDENTIFIER NOT NULL,
	media_set_id INT NOT NULL,
	first_family_number TINYINT,
	first_media_number SMALLINT,
	last_family_number TINYINT,
	last_media_number SMALLINT,
	catalog_family_number TINYINT,
	catalog_media_number SMALLINT,
	position INT,
	expiration_date DATETIME,
	software_vendor_id INT,
	name null,
	description null,
	user_name null,
	software_major_version TINYINT,
	software_minor_version TINYINT,
	software_build_version SMALLINT,
	time_zone SMALLINT,
	mtf_minor_version TINYINT,
	first_lsn NUMERIC(25 , 0),
	last_lsn NUMERIC(25 , 0),
	checkpoint_lsn NUMERIC(25 , 0),
	database_backup_lsn NUMERIC(25 , 0),
	database_creation_date DATETIME,
	backup_start_date DATETIME,
	backup_finish_date DATETIME,
	type CHAR(1),
	sort_order SMALLINT,
	code_page SMALLINT,
	compatibility_level TINYINT,
	database_version INT,
	backup_size NUMERIC(20 , 0),
	database_name null,
	server_name null,
	machine_name null,
	flags INT,
	unicode_locale INT,
	unicode_compare_style INT,
	collation_name null,
	is_password_protected BIT,
	recovery_model null,
	has_bulk_logged_data BIT,
	is_snapshot BIT,
	is_readonly BIT,
	is_single_user BIT,
	has_backup_checksums BIT,
	is_damaged BIT,
	begins_log_chain BIT,
	has_incomplete_metadata BIT,
	is_force_offline BIT,
	is_copy_only BIT,
	first_recovery_fork_guid UNIQUEIDENTIFIER,
	last_recovery_fork_guid UNIQUEIDENTIFIER,
	fork_point_lsn NUMERIC(25 , 0),
	database_guid UNIQUEIDENTIFIER,
	family_guid UNIQUEIDENTIFIER,
	differential_base_lsn NUMERIC(25 , 0),
	differential_base_guid UNIQUEIDENTIFIER,
	compressed_backup_size NUMERIC(20 , 0),
	CONSTRAINT PK__backupse__21F79AAB0E391C95 PRIMARY KEY (backup_set_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE log_shipping_monitor_secondary (
	secondary_server null NOT NULL,
	secondary_database null NOT NULL,
	secondary_id UNIQUEIDENTIFIER NOT NULL,
	primary_server null NOT NULL,
	primary_database null NOT NULL,
	restore_threshold INT NOT NULL,
	threshold_alert INT NOT NULL,
	threshold_alert_enabled BIT NOT NULL,
	last_copied_file null,
	last_copied_date DATETIME,
	last_copied_date_utc DATETIME,
	last_restored_file null,
	last_restored_date DATETIME,
	last_restored_date_utc DATETIME,
	last_restored_latency INT,
	history_retention_period INT NOT NULL,
	CONSTRAINT pklsmonitor_secondary PRIMARY KEY (secondary_id, secondary_database) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysdtspackagelog (
	name null NOT NULL,
	description null,
	id UNIQUEIDENTIFIER NOT NULL,
	versionid UNIQUEIDENTIFIER NOT NULL,
	lineagefull UNIQUEIDENTIFIER NOT NULL,
	lineageshort INT NOT NULL,
	starttime DATETIME NOT NULL,
	endtime DATETIME,
	elapsedtime FLOAT(53),
	computer null NOT NULL,
	operator null NOT NULL,
	logdate DATETIME DEFAULT (getdate()) NOT NULL,
	errorcode INT,
	errordescription null,
	CONSTRAINT PK__sysdtspa__5C158ECB3EDC53F0 PRIMARY KEY (lineagefull) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE log_shipping_primary_databases (
	primary_id UNIQUEIDENTIFIER NOT NULL,
	primary_database null NOT NULL,
	backup_directory null NOT NULL,
	backup_share null NOT NULL,
	backup_retention_period INT NOT NULL,
	backup_job_id UNIQUEIDENTIFIER NOT NULL,
	monitor_server null NOT NULL,
	user_specified_monitor BIT,
	monitor_server_security_mode BIT NOT NULL,
	last_backup_file null,
	last_backup_date DATETIME,
	backup_compression TINYINT DEFAULT ((2)) NOT NULL,
	CONSTRAINT PK__log_ship__ED3BE111005FFE8A PRIMARY KEY (primary_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE syspolicy_management_facets (
	management_facet_id INT NOT NULL,
	name null NOT NULL,
	execution_mode INT NOT NULL,
	CONSTRAINT PK__syspolic__66936FB45EAA0504 PRIMARY KEY (management_facet_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_dac_health_internal (
	dac_name null NOT NULL,
	dac_server_instance_name null NOT NULL,
	is_volume_space_over_utilized INT DEFAULT ((0)) NOT NULL,
	is_volume_space_under_utilized INT DEFAULT ((0)) NOT NULL,
	is_computer_processor_over_utilized INT DEFAULT ((0)) NOT NULL,
	is_computer_processor_under_utilized INT DEFAULT ((0)) NOT NULL,
	is_file_space_over_utilized INT DEFAULT ((0)) NOT NULL,
	is_file_space_under_utilized INT DEFAULT ((0)) NOT NULL,
	is_dac_processor_over_utilized INT DEFAULT ((0)) NOT NULL,
	is_dac_processor_under_utilized INT DEFAULT ((0)) NOT NULL,
	is_policy_overridden BIT DEFAULT ((0)) NOT NULL,
	set_number INT DEFAULT ((0)) NOT NULL,
	processing_time null DEFAULT (sysdatetimeoffset()) NOT NULL,
	CONSTRAINT PK_sysutility_ucp_dac_health_internal_name PRIMARY KEY (set_number, dac_server_instance_name, dac_name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysjobactivity (
	session_id INT NOT NULL,
	job_id UNIQUEIDENTIFIER NOT NULL,
	run_requested_date DATETIME,
	run_requested_source null,
	queued_date DATETIME,
	start_execution_date DATETIME,
	last_executed_step_id INT,
	last_executed_step_date DATETIME,
	stop_execution_date DATETIME,
	job_history_id INT,
	next_scheduled_run_date DATETIME
)
GO

CREATE TABLE syspolicy_policy_category_subscriptions_internal (
	policy_category_subscription_id INT NOT NULL,
	target_type null NOT NULL,
	target_object null NOT NULL,
	policy_category_id INT NOT NULL,
	CONSTRAINT PK_syspolicy_policy_category_subscriptions PRIMARY KEY (policy_category_subscription_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysjobservers (
	job_id UNIQUEIDENTIFIER NOT NULL,
	server_id INT NOT NULL,
	last_run_outcome TINYINT NOT NULL,
	last_outcome_message null,
	last_run_date INT NOT NULL,
	last_run_time INT NOT NULL,
	last_run_duration INT NOT NULL
)
GO

CREATE TABLE sysutility_ucp_processing_state_internal (
	latest_processing_time null,
	latest_health_state_id INT,
	next_health_state_id INT,
	id INT NOT NULL,
	CONSTRAINT PK_sysutility_ucp_processing_state_internal PRIMARY KEY (id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysjobhistory (
	instance_id INT NOT NULL,
	job_id UNIQUEIDENTIFIER NOT NULL,
	step_id INT NOT NULL,
	step_name null NOT NULL,
	sql_message_id INT NOT NULL,
	sql_severity INT NOT NULL,
	message null,
	run_status INT NOT NULL,
	run_date INT NOT NULL,
	run_time INT NOT NULL,
	run_duration INT NOT NULL,
	operator_id_emailed INT NOT NULL,
	operator_id_netsent INT NOT NULL,
	operator_id_paged INT NOT NULL,
	retries_attempted INT NOT NULL,
	server null NOT NULL
)
GO

CREATE TABLE sysdbmaintplans (
	plan_id UNIQUEIDENTIFIER NOT NULL,
	plan_name null NOT NULL,
	date_created DATETIME DEFAULT (getdate()) NOT NULL,
	owner null DEFAULT (isnull(nt_client(),suser_sname())) NOT NULL,
	max_history_rows INT DEFAULT ((0)) NOT NULL,
	remote_history_server null DEFAULT ('') NOT NULL,
	max_remote_history_rows INT DEFAULT ((0)) NOT NULL,
	user_defined_1 INT,
	user_defined_2 null,
	user_defined_3 DATETIME,
	user_defined_4 UNIQUEIDENTIFIER,
	CONSTRAINT PK__sysdbmai__BE9F8F1D7132C993 PRIMARY KEY (plan_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysproxysubsystem (
	subsystem_id INT NOT NULL,
	proxy_id INT NOT NULL
)
GO

CREATE TABLE log_shipping_secondaries (
	primary_id INT,
	secondary_server_name null NOT NULL,
	secondary_database_name null NOT NULL,
	last_copied_filename null,
	last_loaded_filename null,
	last_copied_last_updated DATETIME,
	last_loaded_last_updated DATETIME,
	secondary_plan_id UNIQUEIDENTIFIER,
	copy_enabled BIT,
	load_enabled BIT,
	out_of_sync_threshold INT,
	threshold_alert INT,
	threshold_alert_enabled BIT,
	planned_outage_start_time INT,
	planned_outage_end_time INT,
	planned_outage_weekday_mask INT,
	allow_role_change BIT DEFAULT ((0))
)
GO

CREATE TABLE sysnotifications (
	alert_id INT NOT NULL,
	operator_id INT NOT NULL,
	notification_method TINYINT NOT NULL
)
GO

CREATE TABLE log_shipping_monitor_alert (
	alert_job_id UNIQUEIDENTIFIER NOT NULL,
	CONSTRAINT PK__log_ship__BD57EA57192BAC54 PRIMARY KEY (alert_job_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_policy_target_conditions_internal (
	rollup_object_type INT NOT NULL,
	target_type INT NOT NULL,
	resource_type INT NOT NULL,
	utilization_type INT NOT NULL,
	facet_name null NOT NULL,
	attribute_name null NOT NULL,
	operator_type INT NOT NULL,
	property_name null NOT NULL,
	CONSTRAINT PK_sysutility_ucp_policy_target_condition_internal_type PRIMARY KEY (rollup_object_type, resource_type, target_type, utilization_type, facet_name, attribute_name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysmail_attachments (
	attachment_id INT NOT NULL,
	mailitem_id INT NOT NULL,
	filename null NOT NULL,
	filesize INT NOT NULL,
	attachment IMAGE,
	last_mod_date DATETIME DEFAULT (getdate()) NOT NULL,
	last_mod_user null DEFAULT (suser_sname()) NOT NULL
)
GO

CREATE TABLE systaskids (
	task_id INT NOT NULL,
	job_id UNIQUEIDENTIFIER NOT NULL
)
GO

CREATE TABLE sysoriginatingservers (
	originating_server_id INT DEFAULT ((1)),
	originating_server null NOT NULL,
	master_server BIT DEFAULT ((1))
)
GO

CREATE TABLE syscachedcredentials (
	login_name null NOT NULL,
	has_server_access BIT DEFAULT ((0)) NOT NULL,
	is_sysadmin_member BIT DEFAULT ((0)) NOT NULL,
	cachedate DATETIME DEFAULT (getdate()) NOT NULL,
	CONSTRAINT PK__syscache__F6D56B564CA06362 PRIMARY KEY (login_name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE systargetservers (
	server_id INT NOT NULL,
	server_name null NOT NULL,
	location null,
	time_zone_adjustment INT NOT NULL,
	enlist_date DATETIME DEFAULT CREATE DEFAULT default_current_date AS GETDATE() NOT NULL,
	last_poll_date DATETIME DEFAULT CREATE DEFAULT default_current_date AS GETDATE() NOT NULL,
	status INT DEFAULT CREATE DEFAULT default_one AS 1 NOT NULL,
	local_time_at_last_poll DATETIME NOT NULL,
	enlisted_by_nt_user null NOT NULL,
	poll_interval INT NOT NULL
)
GO

CREATE TABLE sysmail_attachments_transfer (
	transfer_id INT NOT NULL,
	uid UNIQUEIDENTIFIER NOT NULL,
	filename null NOT NULL,
	filesize INT NOT NULL,
	attachment IMAGE,
	create_date DATETIME DEFAULT (getdate()) NOT NULL,
	CONSTRAINT PK__sysmail___78E6FD3327C3E46E PRIMARY KEY (transfer_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_mi_volume_space_health_internal (
	physical_server_name null NOT NULL,
	server_instance_name null NOT NULL,
	volume_device_id null NOT NULL,
	health_state INT NOT NULL,
	set_number INT DEFAULT ((0)) NOT NULL,
	processing_time null DEFAULT (sysdatetimeoffset()) NOT NULL,
	CONSTRAINT PK_sysutility_ucp_mi_volume_space_health_internal_name PRIMARY KEY (set_number, server_instance_name, volume_device_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysschedules (
	schedule_id INT NOT NULL,
	schedule_uid UNIQUEIDENTIFIER NOT NULL,
	originating_server_id INT NOT NULL,
	name null NOT NULL,
	owner_sid VARBINARY(85) NOT NULL,
	enabled INT NOT NULL,
	freq_type INT NOT NULL,
	freq_interval INT NOT NULL,
	freq_subday_type INT NOT NULL,
	freq_subday_interval INT NOT NULL,
	freq_relative_interval INT NOT NULL,
	freq_recurrence_factor INT NOT NULL,
	active_start_date INT NOT NULL,
	active_end_date INT NOT NULL,
	active_start_time INT NOT NULL,
	active_end_time INT NOT NULL,
	date_created DATETIME DEFAULT (getdate()) NOT NULL,
	date_modified DATETIME DEFAULT (getdate()) NOT NULL,
	version_number INT DEFAULT ((1)) NOT NULL,
	CONSTRAINT PK__syssched__C46A8A6F2A4B4B5E PRIMARY KEY (schedule_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_logfiles_stub (
	urn null,
	powershell_path null,
	processing_time null,
	batch_time null,
	server_instance_name null NOT NULL,
	database_name null NOT NULL,
	parent_urn null,
	physical_server_name null NOT NULL,
	volume_name null NOT NULL,
	volume_device_id null NOT NULL,
	Growth REAL,
	GrowthType SMALLINT,
	MaxSize REAL,
	Name null,
	Size REAL,
	UsedSpace REAL,
	FileName null,
	VolumeFreeSpace BIGINT,
	available_space REAL
)
GO

CREATE TABLE sysutility_ucp_aggregated_dac_health_internal (
	dac_count INT DEFAULT ((0)) NOT NULL,
	dac_healthy_count INT DEFAULT ((0)) NOT NULL,
	dac_unhealthy_count INT DEFAULT ((0)) NOT NULL,
	dac_over_utilize_count INT DEFAULT ((0)) NOT NULL,
	dac_under_utilize_count INT DEFAULT ((0)) NOT NULL,
	dac_on_over_utilized_computer_count INT DEFAULT ((0)) NOT NULL,
	dac_on_under_utilized_computer_count INT DEFAULT ((0)) NOT NULL,
	dac_with_files_on_over_utilized_volume_count INT DEFAULT ((0)) NOT NULL,
	dac_with_files_on_under_utilized_volume_count INT DEFAULT ((0)) NOT NULL,
	dac_with_over_utilized_file_count INT DEFAULT ((0)) NOT NULL,
	dac_with_under_utilized_file_count INT DEFAULT ((0)) NOT NULL,
	dac_with_over_utilized_processor_count INT DEFAULT ((0)) NOT NULL,
	dac_with_under_utilized_processor_count INT DEFAULT ((0)) NOT NULL,
	set_number INT DEFAULT ((0)) NOT NULL
)
GO

CREATE TABLE sysmail_configuration (
	paramname null NOT NULL,
	paramvalue null,
	description null,
	last_mod_datetime DATETIME DEFAULT (getdate()) NOT NULL,
	last_mod_user null DEFAULT (suser_sname()) NOT NULL,
	CONSTRAINT SYSMAIL_CONFIGURATION_ParamnameMustBeUnique PRIMARY KEY (paramname) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE restorefilegroup (
	restore_history_id INT NOT NULL,
	filegroup_name null
)
GO

CREATE TABLE syscollector_config_store_internal (
	parameter_name null NOT NULL,
	parameter_value SQL_VARIANT,
	CONSTRAINT PK_syscollector_config_store_internal_paremeter_name PRIMARY KEY (parameter_name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE backupfile (
	backup_set_id INT NOT NULL,
	first_family_number TINYINT,
	first_media_number SMALLINT,
	filegroup_name null,
	page_size INT,
	file_number NUMERIC(10 , 0) NOT NULL,
	backed_up_page_count NUMERIC(10 , 0),
	file_type CHAR(1),
	source_file_block_size NUMERIC(10 , 0),
	file_size NUMERIC(20 , 0),
	logical_name null,
	physical_drive null,
	physical_name null,
	state TINYINT,
	state_desc null,
	create_lsn NUMERIC(25 , 0),
	drop_lsn NUMERIC(25 , 0),
	file_guid UNIQUEIDENTIFIER,
	read_only_lsn NUMERIC(25 , 0),
	read_write_lsn NUMERIC(25 , 0),
	differential_base_lsn NUMERIC(25 , 0),
	differential_base_guid UNIQUEIDENTIFIER,
	backup_size NUMERIC(20 , 0),
	filegroup_guid UNIQUEIDENTIFIER,
	is_readonly BIT,
	is_present BIT,
	CONSTRAINT PK__backupfi__57D1800A17C286CF PRIMARY KEY (backup_set_id, file_number) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE spt_fallback_db (
	xserver_name VARCHAR(30) NOT NULL,
	xdttm_ins DATETIME NOT NULL,
	xdttm_last_ins_upd DATETIME NOT NULL,
	xfallback_dbid SMALLINT,
	name VARCHAR(30) NOT NULL,
	dbid SMALLINT NOT NULL,
	status SMALLINT NOT NULL,
	version SMALLINT NOT NULL
)
GO

CREATE TABLE sysutility_ucp_policy_violations_internal (
	health_policy_id INT NOT NULL,
	policy_id INT NOT NULL,
	policy_name null,
	history_id INT NOT NULL,
	detail_id INT NOT NULL,
	target_query_expression null,
	target_query_expression_with_id null,
	execution_date DATETIME,
	result INT,
	CONSTRAINT PK_sysutility_ucp_policy_violations_internal PRIMARY KEY (policy_id, history_id, detail_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_volumes_stub (
	ID INT NOT NULL,
	virtual_server_name null NOT NULL,
	physical_server_name null NOT NULL,
	volume_device_id null NOT NULL,
	volume_name null NOT NULL,
	total_space_available REAL,
	free_space REAL,
	total_space_utilized REAL,
	percent_total_space_utilization REAL,
	processing_time null,
	batch_time null
)
GO

CREATE TABLE sysutility_ucp_mi_file_space_health_internal (
	server_instance_name null NOT NULL,
	database_name null NOT NULL,
	fg_name null NOT NULL,
	over_utilized_count INT DEFAULT ((0)) NOT NULL,
	under_utilized_count INT DEFAULT ((0)) NOT NULL,
	file_type INT NOT NULL,
	set_number INT DEFAULT ((0)) NOT NULL,
	processing_time null DEFAULT (sysdatetimeoffset()) NOT NULL,
	CONSTRAINT PK_sysutility_ucp_mi_file_space_health_internal_name PRIMARY KEY (set_number, server_instance_name, database_name, fg_name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_policy_check_conditions_internal (
	target_type INT NOT NULL,
	resource_type INT NOT NULL,
	utilization_type INT NOT NULL,
	facet_name null NOT NULL,
	attribute_name null NOT NULL,
	operator_type INT NOT NULL,
	property_name null NOT NULL,
	CONSTRAINT PK_sysutility_ucp_policy_check_condition_internal_type PRIMARY KEY (resource_type, target_type, utilization_type, facet_name, attribute_name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE spt_values (
	name null,
	number INT NOT NULL,
	type null NOT NULL,
	low INT,
	high INT,
	status INT DEFAULT ((0))
)
GO

CREATE TABLE sysutility_ucp_databases_stub (
	urn null,
	powershell_path null,
	processing_time null,
	batch_time null,
	server_instance_name null NOT NULL,
	parent_urn null,
	Collation null,
	CompatibilityLevel SMALLINT,
	CreateDate DATETIME,
	EncryptionEnabled BIT,
	Name null,
	RecoveryModel SMALLINT,
	Trustworthy BIT
)
GO

CREATE TABLE syspolicy_configuration_internal (
	name null NOT NULL,
	current_value SQL_VARIANT NOT NULL,
	CONSTRAINT PK__syspolic__72E12F1A62458BBE PRIMARY KEY (name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE log_shipping_secondary (
	secondary_id UNIQUEIDENTIFIER NOT NULL,
	primary_server null NOT NULL,
	primary_database null NOT NULL,
	backup_source_directory null NOT NULL,
	backup_destination_directory null NOT NULL,
	file_retention_period INT NOT NULL,
	copy_job_id UNIQUEIDENTIFIER NOT NULL,
	restore_job_id UNIQUEIDENTIFIER NOT NULL,
	monitor_server null NOT NULL,
	monitor_server_security_mode BIT NOT NULL,
	user_specified_monitor BIT,
	last_copied_file null,
	last_copied_date DATETIME,
	CONSTRAINT PK__log_ship__DB57D5B20FA2421A PRIMARY KEY (secondary_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE syscollector_collector_types_internal (
	collector_type_uid UNIQUEIDENTIFIER NOT NULL,
	name null NOT NULL,
	parameter_schema null,
	parameter_formatter null,
	schema_collection null,
	collection_package_name null NOT NULL,
	collection_package_folderid UNIQUEIDENTIFIER NOT NULL,
	upload_package_name null NOT NULL,
	upload_package_folderid UNIQUEIDENTIFIER NOT NULL,
	is_system BIT DEFAULT ((0)) NOT NULL,
	CONSTRAINT PK_syscollector_collector_types_internal PRIMARY KEY (collector_type_uid) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE syscollector_collection_items_internal (
	collection_set_id INT NOT NULL,
	collection_item_id INT NOT NULL,
	collector_type_uid UNIQUEIDENTIFIER NOT NULL,
	name null NOT NULL,
	name_id INT,
	frequency INT NOT NULL,
	parameters null,
	CONSTRAINT PK_syscollector_collection_items_internal PRIMARY KEY (collection_set_id, collection_item_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysmail_account (
	account_id INT NOT NULL,
	name null NOT NULL,
	description null,
	email_address null NOT NULL,
	display_name null,
	replyto_address null,
	last_mod_datetime DATETIME DEFAULT (getdate()) NOT NULL,
	last_mod_user null DEFAULT (suser_sname()) NOT NULL,
	CONSTRAINT SYSMAIL_ACCOUNT_IDMustBeUnique PRIMARY KEY (account_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysproxies (
	proxy_id INT NOT NULL,
	name null NOT NULL,
	credential_id INT NOT NULL,
	enabled TINYINT NOT NULL,
	description null,
	user_sid VARBINARY(85) NOT NULL,
	credential_date_created DATETIME NOT NULL,
	CONSTRAINT clust UNIQUE (proxy_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysoperators (
	id INT NOT NULL,
	name null NOT NULL,
	enabled TINYINT NOT NULL,
	email_address null,
	last_email_date INT NOT NULL,
	last_email_time INT NOT NULL,
	pager_address null,
	last_pager_date INT NOT NULL,
	last_pager_time INT NOT NULL,
	weekday_pager_start_time INT NOT NULL,
	weekday_pager_end_time INT NOT NULL,
	saturday_pager_start_time INT NOT NULL,
	saturday_pager_end_time INT NOT NULL,
	sunday_pager_start_time INT NOT NULL,
	sunday_pager_end_time INT NOT NULL,
	pager_days TINYINT NOT NULL,
	netsend_address null,
	last_netsend_date INT NOT NULL,
	last_netsend_time INT NOT NULL,
	category_id INT NOT NULL
)
GO

CREATE TABLE sysutility_ucp_managed_instances_internal (
	instance_id INT NOT NULL,
	instance_name null NOT NULL,
	virtual_server_name null NOT NULL,
	date_created null DEFAULT (sysdatetimeoffset()) NOT NULL,
	created_by null DEFAULT (suser_sname()) NOT NULL,
	agent_proxy_account null NOT NULL,
	cache_directory null,
	management_state INT DEFAULT ((0)) NOT NULL,
	CONSTRAINT PK_sysutility_ucp_mi_name PRIMARY KEY (instance_name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysssispackagefolders (
	folderid UNIQUEIDENTIFIER NOT NULL,
	parentfolderid UNIQUEIDENTIFIER,
	foldername null NOT NULL,
	CONSTRAINT PK_sysssispackagefolders PRIMARY KEY (folderid) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysdtspackages (
	name null NOT NULL,
	id UNIQUEIDENTIFIER NOT NULL,
	versionid UNIQUEIDENTIFIER NOT NULL,
	description null,
	categoryid UNIQUEIDENTIFIER NOT NULL,
	createdate DATETIME,
	owner null NOT NULL,
	packagedata IMAGE,
	owner_sid VARBINARY(85) DEFAULT (suser_sid(N'sa')) NOT NULL,
	packagetype INT DEFAULT ((0)) NOT NULL,
	CONSTRAINT pk_dtspackages PRIMARY KEY (id, versionid) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE syscollector_execution_stats_internal (
	log_id BIGINT NOT NULL,
	task_name null NOT NULL,
	execution_row_count_in INT,
	execution_row_count_out INT,
	execution_row_count_errors INT,
	execution_time_ms INT,
	log_time DATETIME NOT NULL,
	CONSTRAINT PK_syscollector_execution_stats PRIMARY KEY (log_id, task_name, log_time) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysdac_instances_internal (
	instance_id UNIQUEIDENTIFIER NOT NULL,
	instance_name null NOT NULL,
	type_name null NOT NULL,
	type_version null NOT NULL,
	description null DEFAULT (''),
	type_stream IMAGE NOT NULL,
	date_created DATETIME DEFAULT (getdate()) NOT NULL,
	created_by null DEFAULT ([dbo].[fn_sysdac_get_currentusername]()) NOT NULL,
	CONSTRAINT PK_sysdac_instances_internal PRIMARY KEY (instance_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_dacs_stub (
	dac_id INT NOT NULL,
	physical_server_name null NOT NULL,
	server_instance_name null NOT NULL,
	dac_name null NOT NULL,
	dac_deploy_date DATETIME,
	dac_description null,
	urn null,
	powershell_path null,
	processing_time null,
	batch_time null,
	dac_percent_total_cpu_utilization REAL
)
GO

CREATE TABLE sysmail_log (
	log_id INT NOT NULL,
	event_type INT NOT NULL,
	log_date DATETIME DEFAULT (getdate()) NOT NULL,
	description null,
	process_id INT,
	mailitem_id INT,
	account_id INT,
	last_mod_date DATETIME DEFAULT (getdate()) NOT NULL,
	last_mod_user null DEFAULT (suser_sname()) NOT NULL,
	CONSTRAINT sysmail_log_id_MustBeUnique PRIMARY KEY (log_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysmail_server (
	account_id INT NOT NULL,
	servertype null NOT NULL,
	servername null NOT NULL,
	port INT DEFAULT ((25)) NOT NULL,
	username null,
	credential_id INT,
	use_default_credentials BIT DEFAULT ((0)) NOT NULL,
	enable_ssl BIT DEFAULT ((0)) NOT NULL,
	flags INT DEFAULT ((0)) NOT NULL,
	timeout INT,
	last_mod_datetime DATETIME DEFAULT (getdate()) NOT NULL,
	last_mod_user null DEFAULT (suser_sname()) NOT NULL,
	CONSTRAINT SYSMAIL_ACCOUNT_AccountServerTypeMustBeUnique PRIMARY KEY (account_id, servertype) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE restorehistory (
	restore_history_id INT NOT NULL,
	restore_date DATETIME,
	destination_database_name null,
	user_name null,
	backup_set_id INT NOT NULL,
	restore_type CHAR(1),
	replace BIT,
	recovery BIT,
	restart BIT,
	stop_at DATETIME,
	device_count TINYINT,
	stop_at_mark_name null,
	stop_before BIT,
	CONSTRAINT PK__restoreh__FDC4B0311C873BEC PRIMARY KEY (restore_history_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE systargetservergroups (
	servergroup_id INT NOT NULL,
	name null NOT NULL
)
GO

CREATE TABLE sysjobschedules (
	schedule_id INT,
	job_id UNIQUEIDENTIFIER,
	next_run_date INT DEFAULT ((0)) NOT NULL,
	next_run_time INT DEFAULT ((0)) NOT NULL
)
GO

CREATE TABLE MSdbms_map (
	map_id INT NOT NULL,
	src_dbms_id INT NOT NULL,
	dest_dbms_id INT NOT NULL,
	src_datatype_id INT NOT NULL,
	src_len_min BIGINT DEFAULT (NULL),
	src_len_max BIGINT DEFAULT (NULL),
	src_prec_min BIGINT DEFAULT (NULL),
	src_prec_max BIGINT DEFAULT (NULL),
	src_scale_min BIGINT DEFAULT (NULL),
	src_scale_max BIGINT DEFAULT (NULL),
	src_nullable BIT DEFAULT (NULL),
	default_datatype_mapping_id INT DEFAULT (NULL),
	CONSTRAINT pk_MSdbms_map PRIMARY KEY (map_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE syscollector_execution_log_internal (
	log_id BIGINT NOT NULL,
	parent_log_id BIGINT,
	collection_set_id INT NOT NULL,
	collection_item_id INT,
	start_time DATETIME NOT NULL,
	last_iteration_time DATETIME,
	finish_time DATETIME,
	runtime_execution_mode SMALLINT,
	status SMALLINT NOT NULL,
	operator null NOT NULL,
	package_id UNIQUEIDENTIFIER,
	package_execution_id UNIQUEIDENTIFIER,
	failure_message null,
	CONSTRAINT PK_syscollector_execution_log PRIMARY KEY (log_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE spt_monitor (
	lastrun DATETIME NOT NULL,
	cpu_busy INT NOT NULL,
	io_busy INT NOT NULL,
	idle INT NOT NULL,
	pack_received INT NOT NULL,
	pack_sent INT NOT NULL,
	connections INT NOT NULL,
	pack_errors INT NOT NULL,
	total_read INT NOT NULL,
	total_write INT NOT NULL,
	total_errors INT NOT NULL
)
GO

CREATE TABLE syspolicy_object_sets_internal (
	object_set_id INT NOT NULL,
	object_set_name null NOT NULL,
	facet_id INT,
	is_system BIT DEFAULT ((0)) NOT NULL,
	CONSTRAINT PK_syspolicy_object_sets PRIMARY KEY (object_set_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysmail_query_transfer (
	uid UNIQUEIDENTIFIER NOT NULL,
	text_data null,
	create_date DATETIME DEFAULT (getdate()) NOT NULL,
	CONSTRAINT PK__sysmail___DD70126422FF2F51 PRIMARY KEY (uid) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_ucp_filegroups_stub (
	urn null,
	powershell_path null,
	processing_time null,
	batch_time null,
	server_instance_name null NOT NULL,
	database_name null NOT NULL,
	parent_urn null,
	Name null
)
GO

CREATE TABLE MSreplication_options (
	optname null NOT NULL,
	value BIT NOT NULL,
	major_version INT NOT NULL,
	minor_version INT NOT NULL,
	revision INT NOT NULL,
	install_failures INT NOT NULL
)
GO

CREATE TABLE sysutility_ucp_dac_file_space_health_internal (
	dac_name null NOT NULL,
	dac_server_instance_name null NOT NULL,
	fg_name null NOT NULL,
	over_utilized_count INT DEFAULT ((0)) NOT NULL,
	under_utilized_count INT DEFAULT ((0)) NOT NULL,
	file_type INT NOT NULL,
	set_number INT DEFAULT ((0)) NOT NULL,
	processing_time null DEFAULT (sysdatetimeoffset()) NOT NULL,
	CONSTRAINT PK_sysutility_ucp_dac_file_space_health_internal_name PRIMARY KEY (set_number, dac_server_instance_name, dac_name, fg_name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysutility_mi_smo_properties_to_collect_internal (
	object_type INT NOT NULL,
	property_name null NOT NULL,
	CONSTRAINT PK__sysutili__AC2D3E994B2D1C3C PRIMARY KEY (object_type, property_name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysmail_send_retries (
	conversation_handle UNIQUEIDENTIFIER NOT NULL,
	mailitem_id INT NOT NULL,
	send_attempts INT DEFAULT ((1)) NOT NULL,
	last_send_attempt_date DATETIME DEFAULT (getdate()) NOT NULL,
	CONSTRAINT PK__sysmail___C010B7E115A53433 PRIMARY KEY (conversation_handle) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysmail_profileaccount (
	profile_id INT NOT NULL,
	account_id INT NOT NULL,
	sequence_number INT,
	last_mod_datetime DATETIME DEFAULT (getdate()) NOT NULL,
	last_mod_user null DEFAULT (suser_sname()) NOT NULL,
	CONSTRAINT SYSMAIL_ACCOUNT_ProfileAccountMustBeUnique PRIMARY KEY (profile_id, account_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE restorefile (
	restore_history_id INT NOT NULL,
	file_number NUMERIC(10 , 0),
	destination_phys_drive null,
	destination_phys_name null
)
GO

CREATE TABLE log_shipping_monitor_history_detail (
	agent_id UNIQUEIDENTIFIER NOT NULL,
	agent_type TINYINT NOT NULL,
	session_id INT NOT NULL,
	database_name null,
	session_status TINYINT NOT NULL,
	log_time DATETIME NOT NULL,
	log_time_utc DATETIME NOT NULL,
	message null NOT NULL
)
GO

CREATE TABLE sysssislog (
	id INT NOT NULL,
	event null NOT NULL,
	computer null NOT NULL,
	operator null NOT NULL,
	source null NOT NULL,
	sourceid UNIQUEIDENTIFIER NOT NULL,
	executionid UNIQUEIDENTIFIER NOT NULL,
	starttime DATETIME NOT NULL,
	endtime DATETIME NOT NULL,
	datacode INT NOT NULL,
	databytes IMAGE,
	message null NOT NULL,
	CONSTRAINT PK__sysssisl__3213E83F34E8D562 PRIMARY KEY (id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE MSdbms (
	dbms_id INT NOT NULL,
	dbms null NOT NULL,
	version null,
	CONSTRAINT pk_MSdbms PRIMARY KEY (dbms_id) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysssispackages (
	name null NOT NULL,
	id UNIQUEIDENTIFIER NOT NULL,
	description null,
	createdate DATETIME NOT NULL,
	folderid UNIQUEIDENTIFIER NOT NULL,
	ownersid VARBINARY(85) NOT NULL,
	packagedata IMAGE NOT NULL,
	packageformat INT NOT NULL,
	packagetype INT DEFAULT ((0)) NOT NULL,
	vermajor INT NOT NULL,
	verminor INT NOT NULL,
	verbuild INT NOT NULL,
	vercomments null,
	verid UNIQUEIDENTIFIER NOT NULL,
	isencrypted BIT DEFAULT ((0)) NOT NULL,
	readrolesid VARBINARY(85),
	writerolesid VARBINARY(85),
	CONSTRAINT pk_sysssispackages PRIMARY KEY (folderid, name) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE TABLE sysdbmaintplan_jobs (
	plan_id UNIQUEIDENTIFIER NOT NULL,
	job_id UNIQUEIDENTIFIER NOT NULL
)
GO

CREATE TABLE suspect_pages (
	database_id INT NOT NULL,
	file_id INT NOT NULL,
	page_id BIGINT NOT NULL,
	event_type INT NOT NULL,
	error_count INT NOT NULL,
	last_update_date DATETIME DEFAULT (getdate()) NOT NULL
)
GO

CREATE TABLE sysutility_mi_smo_objects_to_collect_internal (
	object_type INT NOT NULL,
	sfc_query null NOT NULL,
	CONSTRAINT PK__sysutili__077EA965475C8B58 PRIMARY KEY (object_type) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)

)
GO

CREATE INDEX logmarkhistory1 ON logmarkhistory (database_name ASC, mark_name ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc4 ON sysjobs (owner_sid ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc1 ON sysdownloadlist (target_server ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX UX_syspolicy_target_sets ON syspolicy_target_sets_internal (object_set_id ASC, type_skeleton ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc1lsprimary_databases ON log_shipping_primary_databases (monitor_server ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc2lsmonitor_history_detail ON log_shipping_monitor_history_detail (database_name ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX NCI_sysutility_resource_health_policies_urn_types ON sysutility_ucp_health_policies_internal (rollup_object_type ASC, target_type ASC, resource_type ASC, utilization_type ASC, policy_name ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc1lsmonitor_secondary ON log_shipping_monitor_secondary (secondary_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX IX_sysmanagement_shared_registered_servers_name ON sysmanagement_shared_registered_servers_internal (name ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc1lssecondary_databases ON log_shipping_secondary_databases (secondary_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc2lssecondary ON log_shipping_secondary (user_specified_monitor ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX IX_sysmanagement_shared_server_groups_clustParentGroupID ON sysmanagement_shared_server_groups_internal (parent_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX spt_valuesclust ON spt_values (type ASC, number ASC, name ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX nc1 ON syssubsystems (subsystem ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX IX_sysmanagement_shared_registered_servers_clustGroupID ON sysmanagement_shared_registered_servers_internal (server_group_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc3lsmonitor_error_detail ON log_shipping_monitor_error_detail (log_time_utc ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX NC_sysjobschedules_schedule_id ON sysjobschedules (schedule_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX backupsetuuid ON backupset (backup_set_uuid ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX c1lsmonitor_history_detail ON log_shipping_monitor_history_detail (agent_id ASC, agent_type ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX nc1 ON sysproxies (name ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc1 ON sysjobhistory (job_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX IX_sysmanagement_shared_server_groups_name ON sysmanagement_shared_server_groups_internal (name ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX IX_syspolicy_system_health_state_internal_target_query_expression_with_id ON syspolicy_system_health_state_internal (target_query_expression_with_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX UX_syspolicy_policy_category_subscriptions ON syspolicy_policy_category_subscriptions_internal (policy_category_id ASC, target_object ASC, target_type ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX backupmediasetuuid ON backupmediaset (media_uuid ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX IX_sysdac_history_internal ON sysdac_history_internal (sequence_id ASC, action_status ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc1lssecondary ON log_shipping_secondary (monitor_server ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc1lsprimary_secondaries ON log_shipping_primary_secondaries (primary_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX nonclust ON syssessions (agent_start_date ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX ix2_spt_values_nu_nc ON spt_values (number ASC, type ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX uc1lsmonitor_secondary ON log_shipping_monitor_secondary (secondary_server ASC, secondary_database ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX c1lsmonitor_error_detail ON log_shipping_monitor_error_detail (agent_id ASC, agent_type ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc1 ON systargetservergroupmembers (server_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX ByName ON sysoperators (name ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX ByID ON sysoperators (id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX ByAlertIDAndOperatorID ON sysnotifications (alert_id ASC, operator_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX IX_syspolicy_policy_execution_history_internal_policy_id ON syspolicy_policy_execution_history_internal (policy_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX ByID ON sysalerts (id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc3 ON sysjobs (category_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc3lsmonitor_secondary ON log_shipping_monitor_secondary (last_restored_latency ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX IX_syspolicy_policy_execution_history_internal_end_date_policy_id ON syspolicy_policy_execution_history_internal (policy_id ASC, end_date ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc1 ON sysjobs (name ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc2lsmonitor_error_detail ON log_shipping_monitor_error_detail (database_name ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX UX_syspolicy_levels ON syspolicy_target_sets_internal (target_set_id ASC, type_skeleton ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc3lsmonitor_history_detail ON log_shipping_monitor_history_detail (log_time_utc ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX backupsetDate ON backupset (backup_finish_date ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX UX_facet_events ON syspolicy_facet_events (management_facet_id ASC, event_name ASC, target_type ASC, target_type_alias ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX IX_syspolicy_system_health_state_internal_policy_id ON syspolicy_system_health_state_internal (policy_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX ByName ON sysalerts (name ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX uc1lsprimary_databases ON log_shipping_primary_databases (primary_database ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX uc1lsmonitor_primary ON log_shipping_monitor_primary (primary_server ASC, primary_database ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX IX_facet_events_target_type_alias ON syspolicy_facet_events (target_type_alias ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc1 ON sysjobservers (server_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX backupmediafamilyuuid ON backupmediafamily (media_family_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX restorehistorybackupset ON restorehistory (backup_set_id ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX nc1 ON sysjobsteps (job_id ASC, step_name ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc2lsmonitor_secondary ON log_shipping_monitor_secondary (restore_threshold ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX nc1 ON systargetservers (server_name ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE UNIQUE INDEX uc1lssecondary ON log_shipping_secondary (primary_server ASC, primary_database ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX nc2lsprimary_databases ON log_shipping_primary_databases (user_specified_monitor ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

CREATE INDEX logmarkhistory2 ON logmarkhistory (database_name ASC, lsn ASC)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysdac_instances_internal ADD CONSTRAINT UQ_sysdac_instances_internal UNIQUE (instance_name ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE syscollector_collection_sets_internal ADD CONSTRAINT UQ_syscollector_collection_sets_internal_name UNIQUE (name ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE syscollector_collection_items_internal ADD CONSTRAINT UQ_syscollector_collection_items_internal_name UNIQUE (name ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysutility_ucp_managed_instances_internal ADD CONSTRAINT UQ_sysutility_ucp_mi_id UNIQUE (instance_id ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysutility_mi_dac_execution_statistics_internal ADD CONSTRAINT UQ__sysutili__A5C7DBB748FABB07 UNIQUE (database_id ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysmanagement_shared_server_groups_internal ADD CONSTRAINT UQ_sysmanagement_unique_group_name_per_parent UNIQUE (parent_id ASC, name ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE syscollector_collector_types_internal ADD CONSTRAINT UQ_syscollector_collection_types_internal_name UNIQUE (name ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysmanagement_shared_registered_servers_internal ADD CONSTRAINT UQ_sysmanagement_unique_server_name_per_group UNIQUE (server_group_id ASC, name ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE syspolicy_policies_internal ADD CONSTRAINT UQ_syspolicy_policies_name UNIQUE (name ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysdbmaintplan_history ADD CONSTRAINT UQ__sysdbmai__B2649B25035179CE UNIQUE (sequence_id ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE syspolicy_conditions_internal ADD CONSTRAINT UQ_syspolicy_conditions_name UNIQUE (name ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysmail_account ADD CONSTRAINT SYSMAIL_ACCOUNT_NameMustBeUnique UNIQUE (name ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysoriginatingservers ADD CONSTRAINT UQ__sysorigi__57F15AAC0EA330E9 UNIQUE (originating_server ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysdbmaintplan_databases ADD CONSTRAINT UQ__sysdbmai__AEEEF1DB7E8CC4B1 UNIQUE (plan_id ASC, database_name ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE log_shipping_primary_databases ADD CONSTRAINT UQ__log_ship__2A5EF6DC033C6B35 UNIQUE (primary_database ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysdtscategories ADD CONSTRAINT uq_dtscategories_name_parent UNIQUE (name ASC, parentid ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysutility_mi_dac_execution_statistics_internal ADD CONSTRAINT UQ__sysutili__0717EC614BD727B2 UNIQUE (database_name ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysmail_profile ADD CONSTRAINT SYSMAIL_PROFILE_NameMustBeUnique UNIQUE (name ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE syspolicy_policy_categories_internal ADD CONSTRAINT UQ_syspolicy_policy_categories_name UNIQUE (name ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysdbmaintplan_jobs ADD CONSTRAINT UQ__sysdbmai__F87CA47779C80F94 UNIQUE (plan_id ASC, job_id ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysdtspackages ADD CONSTRAINT UQ__sysdtspa__E77790D92CBDA3B5 UNIQUE (versionid ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysoriginatingservers ADD CONSTRAINT UQ__sysorigi__D65E569C0BC6C43E UNIQUE (originating_server_id ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysdac_history_internal ADD CONSTRAINT UQ_sysdac_history_internal UNIQUE (action_id ASC, dac_object_type ASC, action_type ASC, dac_object_name_pretran ASC, dac_object_name_posttran ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE syspolicy_object_sets_internal ADD CONSTRAINT UQ_syspolicy_object_sets_name UNIQUE (object_set_name ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

ALTER TABLE sysssispackagefolders ADD CONSTRAINT U_sysssispackagefoldersuniquepath UNIQUE (parentfolderid ASC, foldername ASC) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
GO

SET ANSI_PADDING OFF
GO

