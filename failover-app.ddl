DROP TABLE public.msg_data;

CREATE TABLE public.msg_data
(
  message_id character varying(255) NOT NULL,
  message_body character varying(255),
  CONSTRAINT msg_data_pkey PRIMARY KEY (message_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.msg_data
  OWNER TO xeon;


-- to be used with postgresql
