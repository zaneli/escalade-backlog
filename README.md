#Backlog4j
[Backlog API](http://www.backlog.jp/api/ "Backlog API") �� Scala ���b�p�[���C�u�����ł��B

##���API�̎g�p
    val client = BacklogClientFactory(<�X�y�[�XID>, <���[�U�[ID>, <�p�X���[�h>).createClient
    client.getProjects
    client.getUser(<���[�UID or ���O�C��ID>)
    client.findIssue(FindIssueParamBuilder(<�v���W�F�N�gID>).build)
    client.findIssue(
        FindIssueParamBuilder(<�v���W�F�N�gID>).componentId(<�J�e�S��ID>).milestoneId(<�}�C���X�g�[��ID1>, <�}�C���X�g�[��ID2>).build)

##�Ǘ��Ҍ���API�̎g�p
    val client = BacklogClientFactory(<�X�y�[�XID>, <���[�U�[ID>, <�p�X���[�h>).createAdminClient
    client.getProjects